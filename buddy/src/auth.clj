(ns auth
  (:require
   [ring.util.response]
   [buddy.sign.jwt :as jwt]))

(defn login [users secret]
  (fn [{{name :name pass :pass} :body}]
    (if (and (not (nil? name))
             (not (nil? pass))
             (not (nil? (get users name)))
             (= (get users name) pass))
      (jwt/sign {:user name} secret)
      "")))

(defn unsign [secret]
  (fn [bearer-token]
    (jwt/unsign bearer-token secret)))