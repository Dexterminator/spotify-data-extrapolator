(ns spotify-data-extrapolator.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [spotify-data-extrapolator.core-test]))

(doo-tests 'spotify-data-extrapolator.core-test)
