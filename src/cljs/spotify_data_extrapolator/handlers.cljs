(ns spotify-data-extrapolator.handlers
  (:require [re-frame.core :refer [register-handler dispatch]]
            [spotify-data-extrapolator.db :as db]
            [spotify-data-extrapolator.api :as api]
            [clojure.string :as str]
            [spotify-data-extrapolator.js-utils :refer [log]])
  (:require-macros [spotify-data-extrapolator.macros :refer [inspect]]))

(defn artist-data [artists]
  (map #(-> (select-keys % [:name :id])
            (assoc :image ((comp :url first :images) %))) artists))

(register-handler
  :initialize-db
  (fn [_ _]
    db/default-db))

(register-handler
  :set-active-panel
  (fn [db [_ active-panel]]
    (assoc db :active-panel active-panel)))

(register-handler
  :artist-search-changed
  (fn [db [_ artist-search]]
    (dispatch [:get-artists artist-search])
    db))

(register-handler
  :artists-response
  (fn [db [_ response]]
    (let [clj-response (js->clj response)
          items (get-in clj-response [:artists :items])
          artists (artist-data items)]
      (assoc db :artists artists))))

(register-handler
  :failed-response
  (fn [db [_ {:keys [status status-text]}]]
    (log (str "Something went wrong: " status " " status-text))
    db))

(register-handler
  :get-artists
  (fn [db [_ artist]]
    (if-not (str/blank? artist)
      (do (api/search-artists
            artist
            {:handler       #(dispatch [:artists-response %])
             :error-handler #(dispatch [:failed-response %])})
          db)
      (dissoc db :artists))))

(register-handler
  :related-artists-response
  (fn [db [_ response]]
    (let [clj-response (js->clj response)
          items (clj-response :artists)
          artists (artist-data items)]
      (dispatch [:set-active-panel :inspired-by-panel])
      (-> db
          (dissoc :artists)
          (assoc :inspired-by-artists artists)))))

(register-handler
  :inspired-by-search
  (fn [db [_ id]]
    (api/related-artists
      id
      {:handler       #(dispatch [:related-artists-response %])
       :error-handler #(dispatch [:failed-response %])})
    db))
