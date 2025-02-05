(ns clojure-ddd-hexagonal.infrastructure.repository.user
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            [clojure-ddd-hexagonal.domain.user :as d-user]))

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
    (sql/query (get-datasource) ["SELECT * FROM users"]))

  (get-by-id [_ id]
    (sql/get-by-id (get-datasource) :users id))

  (create-user [_ user]
    (sql/insert! (get-datasource) :users user))

  (update-user [_ id user]
    (let [result (sql/update! (get-datasource) :users user {:id id})]
      (if (empty? result)
        (throw (ex-info "User not found" {:id id}))
        result)))

  (delete-user [_ id]
    (let [result (sql/delete! (get-datasource) :users {:id id})]
      (if (empty? result)
        (throw (ex-info "User not found" {:id id}))
        result))))
