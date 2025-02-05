(ns clojure-ddd-hexagonal.infrastructure.rest.controllers.user
  (:require [ring.util.http-response :as response]
            [clojure-ddd-hexagonal.application.usecase.user :as usecase-user]))

(defn get-all-handler [repository _]
  (try
    (response/ok (usecase-user/get-all-users repository))
    (catch Exception e
      (response/internal-server-error {:error (.getMessage e)}))))

(defn create-user-handler [repository request]
  (try
    (let [new-user (:body request)
          user-cerated (usecase-user/create-user repository new-user)]
      (response/created "/users" user-cerated))
    (catch Exception e
      (response/internal-server-error {:error (.getMessage e)}))))

(defn get-by-id-handler [repository {params :params}]
  (try
    (let [id (Integer/parseInt (get params "id"))
          user (usecase-user/get-by-id repository id)]
      (if user
        (response/ok user)
        (response/not-found {:error "User not found"})))
    (catch Exception e
      (response/internal-server-error {:error (.getMessage e)}))))

(defn update-user-handler [repository {params :params body :body}]
  (try
    (let [id (Integer/parseInt (get params "id"))
          result (usecase-user/update-user repository id body)]
      (if result
        (response/ok {:message "User updated"})
        (response/not-found {:error "User not found"})))
    (catch Exception e
      (response/internal-server-error {:error (.getMessage e)}))))

(defn delete-user-handler [repository {params :params}]
  (try
    (let [id (Integer/parseInt (get params "id"))
          result (usecase-user/delete-user repository id)]
      (if result
        (response/no-content)
        (response/not-found {:error "User not found"})))
    (catch Exception e
      (response/internal-server-error {:error (.getMessage e)}))))
