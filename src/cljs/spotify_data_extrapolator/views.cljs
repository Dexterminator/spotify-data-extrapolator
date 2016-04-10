(ns spotify-data-extrapolator.views
    (:require [re-frame.core :as re-frame]))

(defn app-header []
  [:h1.app-header "Spotify data extrapolator"])

;; home

(defn home-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div
       [app-header]
       [:div "Hello from " [:span.app-name @name] ". This is the Home Page."]
       [:div.btn "Search"]
       [:div [:a {:href "#/about"} "go to About Page"]]])))


;; about

(defn about-panel []
  (fn []
    [:div
     [app-header]
     [:div "Made with Clojure by Dexter Gramfors and Andreas Johansson."]
     [:div [:a {:href "#/"} "go to Home Page"]]]))


;; main

(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :about-panel [] [about-panel])
(defmethod panels :default [] [:div])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      (panels @active-panel))))
