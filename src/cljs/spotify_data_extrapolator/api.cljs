(ns spotify-data-extrapolator.api
  (:require [ajax.core :refer [GET]]))

(def spotify-base-url "https://api.spotify.com/v1")

(defn get-artists [artist options]
  (GET (str spotify-base-url "/search") (merge options {:params {:q artist :type "artist"}})))
