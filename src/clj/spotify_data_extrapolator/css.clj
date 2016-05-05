(ns spotify-data-extrapolator.css
  (:require [garden.def :refer [defstyles defcssfn]]
            [garden.stylesheet :refer [at-import]]
            [garden.selectors :refer [defpseudoelement defselector css-selector] :as s]
            [garden.core :refer [css]]))

(def spotify-green "#23CF5F")
(def dark-grey "#424242")
(def page-width "980px")
(defcssfn url)
(defpseudoelement -webkit-scrollbar)
(defpseudoelement -webkit-scrollbar-thumb)
(s/defclass artists)
(def artist-scrollbar (s/selector (artists -webkit-scrollbar)))
(def artist-scrollbar-thumb (s/selector (artists -webkit-scrollbar-thumb)))
(css [artist-scrollbar-thumb {:border-radius      "4px"
                              :background-color   "rgba (0, 0, 0, .5)"
                              :-webkit-box-shadow "0 0 1px rgba (255, 255, 255, .5)"}])


(defstyles screen
  (at-import (url "https://fonts.googleapis.com/css?family=Lato:100,300,400,700,900,100italic,300italic,400italic,700italic,900italic"))
  [:body {:font-family ["Lato" "sans-serif"]
          :font-size   "20px"
          :font-weight 300
          :width       page-width
          :margin      "0 auto"
          :padding     0}]
  [:a {:text-decoration "none"
       :color           dark-grey}
   [:&:hover {:cursor "pointer"
              :color  spotify-green}]]
  [:.app-header {:color       spotify-green
                 :font-size   "80px"
                 :font-weight 300}]
  [:.btn {:color         spotify-green
          :padding       "10px 20px 10px 20px"
          :margin-top    "10px"
          :margin-bottom "10px"
          :background    dark-grey
          :border-radius "10px"
          :display       "inline-block"}
   [:&:hover {:background "grey"
              :cursor     "pointer"}]
   [:&:active {:background "black"}]]
  [:.artist-image {:height        "150px"
                   :border-radius "5px"}]
  [:.artists {:height   "300px"
              :overflow "scroll"}]
  [:.artist {:font-size   "20px"
             :font-weight 400}]
  [artist-scrollbar {:-webkit-appearance "none"
                     :width              "7px"}]
  [artist-scrollbar-thumb {:border-radius      "4px"
                           :background-color   "rgba(0, 0, 0, .5)"
                           :-webkit-box-shadow "0 0 1px rgba (255, 255, 255, .5)"}])
