(ns spotify-data-extrapolator.handlers
  (:require [re-frame.core :refer [register-handler dispatch trim-v]]
            [spotify-data-extrapolator.db :as db]
            [spotify-data-extrapolator.api :as api]
            [clojure.string :as str]
            [spotify-data-extrapolator.js-utils :refer [log]])
  (:require-macros [spotify-data-extrapolator.macros :refer [inspect]]))

(defn artist-data [artists]
  (map #(-> (select-keys % [:name :id])
            (assoc :image ((comp :url first :images) %))) artists))

(defn set-active-panel-handler [db [active-panel]]
  (assoc db :active-panel active-panel))

(defn artist-search-changed-handler [db [artist-search]]
  (dispatch [:get-artists artist-search])
  db)

(defn artists-response-handler [db [response]]
  (let [clj-response (js->clj response)
        items (get-in clj-response [:artists :items])
        artists (artist-data items)]
    (assoc db :artists artists)))

(defn failed-response-handler [db [{:keys [status status-text]}]]
  (log (str "Something went wrong: " status " " status-text))
  db)

(defn get-artists-handler [db [artist]]
  (if-not (str/blank? artist)
      (do (api/search-artists
            artist
            {:handler       #(dispatch [:artists-response %])
             :error-handler #(dispatch [:failed-response %])})
          db)
      (dissoc db :artists)))

(defn related-artists-response-handler [db [response]]
  (let [clj-response (js->clj response)
          items (clj-response :artists)
          artists (artist-data items)]
      (dispatch [:set-active-panel :inspired-by-panel])
      (-> db
          (dissoc :artists)
          (assoc :inspired-by-artists artists))))

(defn inspired-by-search-handler [db [id]]
  (api/related-artists
      id
      {:handler       #(dispatch [:related-artists-response %])
       :error-handler #(dispatch [:failed-response %])})
  db)

(register-handler
  :initialize-db
  (fn [_ _]
    db/default-db))

(register-handler
  :set-active-panel
  trim-v
  set-active-panel-handler)

(register-handler
  :artist-search-changed
  trim-v
  artist-search-changed-handler)

(register-handler
  :artists-response
  trim-v
  artists-response-handler)

(register-handler
  :failed-response
  trim-v
  failed-response-handler)

(register-handler
  :get-artists
  trim-v
  get-artists-handler)

(register-handler
  :related-artists-response
  trim-v
  related-artists-response-handler)

(register-handler
  :inspired-by-search
  trim-v
  inspired-by-search-handler)
