(ns spotify-data-extrapolator.handlers
  (:require [re-frame.core :refer [register-handler dispatch]]
            [spotify-data-extrapolator.db :as db]
            [spotify-data-extrapolator.api :as api]))

(register-handler
  :initialize-db
  (fn [_ _]
    db/default-db))

(register-handler
  :set-active-panel
  (fn [db [_ active-panel]]
    (assoc db :active-panel active-panel)))

(register-handler
  :artists-response
  (fn [db [_ response]]
    (let [artists (js->clj response)
          items (get-in artists [:artists :items])
          names (map :name items)]
      (assoc db :artists names))))

(register-handler
  :failed-response
  (fn [db [_ {:keys [status status-text]}]]
    (.log js/console (str "Something went wrong: " status " " status-text))))

(register-handler
  :get-artists
  (fn [db [_ artist]]
    (api/get-artists
      artist
      {:handler       #(dispatch [:artists-response %])
       :error-handler #(dispatch [:failed-response %])})
    (assoc db :loading? true)))
