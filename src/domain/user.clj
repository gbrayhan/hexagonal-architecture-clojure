(ns domain.user)

(defrecord User [id name email created-at])

(defprotocol IUserService
  (get-all [this])
  (get-by-id [this id])
  (create-user [this user])
  (update-user [this id user])
  (delete-user [this id]))
