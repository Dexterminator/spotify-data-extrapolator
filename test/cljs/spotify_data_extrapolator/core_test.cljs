(ns spotify-data-extrapolator.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [spotify-data-extrapolator.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
