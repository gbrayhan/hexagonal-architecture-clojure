(defproject clojure-ddd-hexagonal "0.1.0-SNAPSHOT"
  :description "REST API en Clojure con Arquitectura Hexagonal"
  :resource-paths ["resources"]
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [compojure "1.6.2"]
                 [ring/ring-defaults "0.3.3"]
                 [metosin/reitit "0.5.15"]
                 [org.postgresql/postgresql "42.3.1"]
                 [seancorfield/next.jdbc "1.2.659"]
                 [migratus "1.3.6"]
                 [metosin/ring-http-response "0.9.4"]
                 [ring/ring-jetty-adapter "1.9.6"]
                 [org.clojure/tools.cli "1.0.206"]
                 [cheshire "5.10.0"]
                 [ring/ring-json "0.5.1"]]
  :main ^:skip-aot core
  :uberjar-name "clojure-ddd-hexagonal.jar"
  :profiles {:uberjar {:aot :all}}
  :aliases {"migrate"  ["run" "-m" "core" "migrate"]
            "rollback" ["run" "-m" "core" "rollback"]})
