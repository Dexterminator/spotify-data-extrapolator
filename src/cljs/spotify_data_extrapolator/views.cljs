(ns spotify-data-extrapolator.views
  (:require [re-frame.core :refer [subscribe dispatch]]))

(defn app-header []
  (let [artist (subscribe [:artist])]
    (fn []
      [:div
       [:a {:href "#"}
        [:h1.app-header "Spotify data extrapolator"]]
       [:h2.artist @artist]])))


;; home

(defn artist-list []
  (let [artists (subscribe [:artists])]
    (fn []
      [:div {:class "artists"}
       (for [artist @artists]
         ^{:key artist} [:div
                         [:a.artist {:href (str "#/inspired-by/" (:id artist))}
                          (:name artist)]])])))

(defn home-panel []
  (fn []
    [:div
     [app-header]
     [:div "Start typing to search!"]
     [:div [:input {:type "text" :on-change #(dispatch [:artist-search-changed (-> % .-target .-value)])}]]
     [artist-list]]))

;; inspired by

(defn inspired-by-panel []
  (let [inspired-by-artists (subscribe [:inspired-by-artists])]
    (fn []
      [:div
       [app-header]
       [:div
        (if (seq @inspired-by-artists)
          (for [artist @inspired-by-artists]
            ^{:key artist} [:div [:a.artist {:href (str "#/inspired-by/" (:id artist))}
                                  (:name artist)]])
          [:div "Did not find any influencing artists."])]])))

;; main

(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :inspired-by-panel [] [inspired-by-panel])
(defmethod panels :default [] [:div])

(defn main-panel []
  (let [active-panel (subscribe [:active-panel])]
    (fn []
      (panels @active-panel))))
