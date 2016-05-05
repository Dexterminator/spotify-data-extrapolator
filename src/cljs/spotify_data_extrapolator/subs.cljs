(ns spotify-data-extrapolator.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :name
 (fn [db]
   (reaction (:name @db))))

(re-frame/register-sub
 :active-panel
 (fn [db _]
   (reaction (:active-panel @db))))

(re-frame/register-sub
  :artists
  (fn [db _]
    (reaction (:artists @db))))

(re-frame/register-sub
  :inspired-by-artists
  (fn [db _]
    (reaction (:inspired-by-artists @db))))
