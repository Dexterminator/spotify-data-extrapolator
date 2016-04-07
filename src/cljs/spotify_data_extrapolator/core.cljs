(ns spotify-data-extrapolator.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [spotify-data-extrapolator.handlers]
              [spotify-data-extrapolator.subs]
              [spotify-data-extrapolator.routes :as routes]
              [spotify-data-extrapolator.views :as views]
              [spotify-data-extrapolator.config :as config]))

(when config/debug?
  (println "dev mode"))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init [] 
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
