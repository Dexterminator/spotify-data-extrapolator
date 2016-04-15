(ns spotify-data-extrapolator.handlers
  (:require [re-frame.core :refer [register-handler dispatch]]
            [spotify-data-extrapolator.db :as db]
            [spotify-data-extrapolator.api :as api]
            [clojure.string :as str]))

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
    (.log js/console artist-search)
    (dispatch [:get-artists artist-search])
    db))

(register-handler
  :artists-response
  (fn [db [_ response]]
    (let [clj-response (js->clj response)
          items (get-in clj-response [:artists :items])
          artists (map #(-> (select-keys % [:name :id])
                            (assoc :image ((comp :url first :images) %)))
                       items)]
      (assoc db :artists artists))))

(register-handler
  :failed-response
  (fn [_ [_ {:keys [status status-text]}]]
    (.log js/console (str "Something went wrong: " status " " status-text))))

(register-handler
  :get-artists
  (fn [db [_ artist]]
    (if-not (str/blank? artist)
      (do (api/get-artists
            artist
            {:handler       #(dispatch [:artists-response %])
             :error-handler #(dispatch [:failed-response %])})
          db)
      (assoc db :artists nil))))
