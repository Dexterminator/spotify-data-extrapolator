(defproject spotify-data-extrapolator "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [reagent "0.5.1"]
                 [re-frame "0.7.0"]
                 [secretary "1.2.3"]
                 [garden "1.3.2"]
                 [compojure "1.5.0"]
                 [ring "1.4.0"]
                 [cljs-ajax "0.5.4"]
                 [figwheel-sidecar "0.5.0"]
                 [binaryage/devtools "0.6.1"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "script"]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-garden "0.2.6"]
            [lein-doo "0.1.6"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "test/js"
                                    "resources/public/css/compiled"]

  :figwheel {:css-dirs ["resources/public/css"]
             :ring-handler spotify-data-extrapolator.handler/handler}

  :garden {:builds [{:id "screen"
                     :source-paths ["src/clj"]
                     :stylesheet spotify-data-extrapolator.css/screen
                     :compiler {:output-to "resources/public/css/compiled/screen.css"
                                :pretty-print? true}}]}

    :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel {:on-jsload "spotify-data-extrapolator.core/mount-root"}
                        :compiler {:main spotify-data-extrapolator.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :source-map-timestamp true}}

                       {:id "test"
                        :source-paths ["src/cljs" "test/cljs"]
                        :compiler {:output-to "resources/public/js/compiled/test.js"
                                   :main spotify-data-extrapolator.runner
                                   :optimizations :none}}

                       {:id "min"
                        :source-paths ["src/cljs"]
                        :compiler {:main spotify-data-extrapolator.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :optimizations :advanced
                                   :closure-defines {goog.DEBUG false}
                                   :pretty-print false}}]})
