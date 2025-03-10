(ns infrastructure.repository.user
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            [domain.user :as d-user]
            [infrastructure.repository.adapters.user-adapter :as repository-adapter]
            [clojure.walk :refer [keywordize-keys]]))

(defn get-datasource []
  (jdbc/get-datasource {:dbtype "postgresql"
                         :host "db"
                         :port 5432
                         :dbname "mi_api_rest_db"
                         :user "usuario"
                         :password "password"}))

(extend-type nil
  d-user/IUserService
  (get-all [_]
    (let [rows (sql/query (get-datasource)
                          ["SELECT * FROM users"]
                          {:row-fn keywordize-keys})]
      (println "Raw rows:" rows) ;; Debug: imprime los registros tal como vienen de la consulta
      (->> rows
           (map repository-adapter/db->domain)
           (vec))))
  (get-by-id [_ id]
    (some-> (sql/get-by-id (get-datasource) :users id)
            keywordize-keys
            repository-adapter/db->domain))
  (create-user [_ user]
    (let [db-user (repository-adapter/domain->db user)
          inserted (sql/insert! (get-datasource)
                                :users
                                db-user
                                {:return-keys true})]
      (repository-adapter/db->domain inserted)))
  (update-user [_ id user]
    (let [db-user (repository-adapter/domain->db user)
          result (sql/update! (get-datasource) :users db-user {:id id})]
      (if (zero? result)
        (throw (ex-info "User not found" {:id id}))
        (some-> (sql/get-by-id (get-datasource) :users id)
                keywordize-keys
                repository-adapter/db->domain))))
  (delete-user [_ id]
    (let [existing (sql/get-by-id (get-datasource) :users id)]
      (if (nil? existing)
        (throw (ex-info "User not found" {:id id}))
        (do
          (sql/delete! (get-datasource) :users {:id id})
          (repository-adapter/db->domain (keywordize-keys existing)))))))
