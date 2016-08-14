(ns spotify-data-extrapolator.api
  (:require [ajax.core :refer [GET]]))

(def spotify-base-url "https://api.spotify.com/v1")
(def default-options {:response-format :json
                      :keywords?       true})

(defn search-artists [artist options]
  (GET (str spotify-base-url "/search") (merge options {:params {:q artist :type "artist"}} default-options)))

(defn related-artists [id options]
  (GET (str spotify-base-url "/artists/" id "/related-artists") (merge options default-options)))

(defn artist [id options]
  (GET (str spotify-base-url "/artists/" id) (merge options default-options)))

(defn artist-albums [id options]
  (GET (str spotify-base-url "/artists/" id "/albums") (merge options default-options)))
