(ns spotify-data-extrapolator.views
  (:require [re-frame.core :refer [subscribe dispatch]]))

(defn app-header []
  [:h1.app-header "Spotify data extrapolator"])

;; home

(defn artist-list []
  (let [artists (subscribe [:artists])]
    (fn []
      [:div {:class "artists"}
       (for [artist @artists]
         ^{:key artist} [:div.artist (:name artist)])])))

(defn home-panel []
  (fn []
    [:div
     [app-header]
     [:div "Start typing to search!"]
     [:div [:input {:type "text" :on-change #(dispatch [:artist-search-changed (-> % .-target .-value)])}]]
     [artist-list]
     [:div [:a {:href "#/about"} "go to About Page"]]]))


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
  (let [active-panel (subscribe [:active-panel])]
    (fn []
      (panels @active-panel))))
