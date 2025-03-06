(ns clojure-ddd-hexagonal.infrastructure.repository.user
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            [clojure-ddd-hexagonal.domain.user :as d-user]
            [clojure-ddd-hexagonal.infrastructure.repository.adapters.user-adapter :as repository-adapter]))

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
    (->> (sql/query (get-datasource) ["SELECT * FROM users"])
         (map repository-adapter/db->domain)
         (vec)))
  (get-by-id [_ id]
    (some-> (sql/get-by-id (get-datasource) :users id)
            repository-adapter/db->domain))
  (create-user [_ user]
    (let [db-user (repository-adapter/domain->db user)
          inserted (sql/insert! (get-datasource) :users db-user)]
      (repository-adapter/db->domain inserted)))
  (update-user [_ id user]
    (let [db-user (repository-adapter/domain->db user)
          result (sql/update! (get-datasource) :users db-user {:id id})]
      (if (zero? result)
        (throw (ex-info "User not found" {:id id}))
        (some-> (sql/get-by-id (get-datasource) :users id)
                repository-adapter/db->domain))))
  (delete-user [_ id]
    (let [existing (sql/get-by-id (get-datasource) :users id)]
      (if (nil? existing)
        (throw (ex-info "User not found" {:id id}))
        (do
          (sql/delete! (get-datasource) :users {:id id})
          (repository-adapter/db->domain existing))))))
