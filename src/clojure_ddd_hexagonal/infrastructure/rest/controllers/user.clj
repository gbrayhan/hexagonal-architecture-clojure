(ns clojure-ddd-hexagonal.infrastructure.rest.controllers.user
   (:require [ring.util.http-response :as response]
             [clojure-ddd-hexagonal.application.usecase.user :as usecase-user]
             [clojure-ddd-hexagonal.infrastructure.rest.controllers.adapters.user-adapter :as controllers-adapter]))

 (defn get-all-handler [repository _]
   (try
     (let [users (usecase-user/get-all-users repository)
           users-dto (map controllers-adapter/domain->dto users)]
       (response/ok users-dto))
     (catch Exception e
       (response/internal-server-error {:error (.getMessage e)}))))

 (defn create-user-handler [repository request]
   (try
     (let [new-user (:body request)
           created-user (usecase-user/create-user repository new-user)
           created-user-dto (controllers-adapter/domain->dto created-user)]
       (response/created "/user" created-user-dto))
     (catch Exception e
       (response/internal-server-error {:error (.getMessage e)}))))

 (defn get-by-id-handler [repository {params :params}]
   (try
     (let [id (Integer/parseInt (get params "id"))
           user (usecase-user/get-by-id repository id)]
       (if user
         (response/ok (controllers-adapter/domain->dto user))
         (response/not-found {:error "User not found"})))
     (catch Exception e
       (response/internal-server-error {:error (.getMessage e)}))))

 (defn update-user-handler [repository {params :params body :body}]
   (try
     (let [id (Integer/parseInt (get params "id"))
           updated-user (usecase-user/update-user repository id body)]
       (if updated-user
         (response/ok {:message "User updated"
                       :user (controllers-adapter/domain->dto updated-user)})
         (response/not-found {:error "User not found"})))
     (catch Exception e
       (response/internal-server-error {:error (.getMessage e)}))))

 (defn delete-user-handler [repository {params :params}]
   (try
     (let [id (Integer/parseInt (get params "id"))
           deleted-user (usecase-user/delete-user repository id)]
       (if deleted-user
         (response/no-content)
         (response/not-found {:error "User not found"})))
     (catch Exception e
       (response/internal-server-error {:error (.getMessage e)}))))
