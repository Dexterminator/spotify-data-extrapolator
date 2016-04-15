(ns spotify-data-extrapolator.views
  (:require [re-frame.core :refer [subscribe dispatch]]))

(defn app-header []
  [:h1.app-header "Spotify data extrapolator"])

;; home

(defn home-panel []
  (let [name (subscribe [:name])
        artists (subscribe [:artists])]
    (fn []
      [:div
       [app-header]
       [:div "Hello from " [:span.app-name @name] ". This is the Home Page."]
       [:div.btn {:on-click #(dispatch [:get-artists "Black"])} "Search"]
       [:div.artists
        (for [artist @artists]
          ^{:key artist} [:div
                          [:h3 (:name artist)]
                          [:img.artist-image {:src (:image artist)}]])]
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
  (let [active-panel (subscribe [:active-panel])]
    (fn []
      (panels @active-panel))))
