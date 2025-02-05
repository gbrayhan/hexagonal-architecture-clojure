(ns clojure-ddd-hexagonal.core
  (:require [migratus.core :as migratus]
            [clojure-ddd-hexagonal.infrastructure.rest.routes :refer [raw-routes]]
            [ring.adapter.jetty :as jetty]
            [clojure.tools.cli :refer [parse-opts]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]])
  (:gen-class))

(defn read-config []
  (clojure.edn/read-string (slurp "resources/migratus.edn")))

(defn run-migrations []
  (println "Running migrations...")
  (migratus/migrate (read-config)))

(defn rollback-migrations []
  (println "Rolling back migrations...")
  (migratus/rollback (read-config)))

(def app-routes
  (-> raw-routes
      (wrap-json-body {:keywords? true})
      (wrap-json-response)))

(defn start-server []
  (jetty/run-jetty
    app-routes
    {:port 3000 :join? false}))

(defn -main [& args]
  (let [{:keys [arguments]} (parse-opts args [])]
    (cond
      (some #{"migrate"} args)
      (do
        (run-migrations)
        (System/exit 0))

      (some #{"rollback"} args)
      (do
        (rollback-migrations)
        (System/exit 0))

      :else
      (do
        (run-migrations)
        (start-server)))))
