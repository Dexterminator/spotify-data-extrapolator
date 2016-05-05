(ns spotify-data-extrapolator.api
  (:require [ajax.core :refer [GET]]))

(def spotify-base-url "https://api.spotify.com/v1")

(defn search-artists [artist options]
  (GET (str spotify-base-url "/search") (merge options {:params {:q artist :type "artist"}
                                                        :response-format :json
                                                        :keywords? true})))

(defn related-artists [id options]
  (GET (str spotify-base-url "/artists/" id "/related-artists") (merge options {:response-format :json
                                                                                :keywords? true})))
