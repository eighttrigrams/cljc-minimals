(ns main
  (:import
   java.io.File
   org.apache.lucene.store.FSDirectory
   #_org.apache.lucene.store.RAMDirectory
   org.apache.lucene.analysis.standard.StandardAnalyzer
   org.apache.lucene.index.IndexWriterConfig
   org.apache.lucene.document.Document
   org.apache.lucene.document.Field$Store
   org.apache.lucene.document.StringField
   org.apache.lucene.index.DirectoryReader
   org.apache.lucene.index.IndexWriter
   org.apache.lucene.search.IndexSearcher
   org.apache.lucene.search.PrefixQuery
   org.apache.lucene.queryparser.classic.MultiFieldQueryParser
   org.apache.lucene.search.BooleanQuery$Builder
   org.apache.lucene.index.Term
   org.apache.lucene.search.BooleanClause$Occur))

(def MUST BooleanClause$Occur/MUST)
(def MUST_NOT BooleanClause$Occur/MUST_NOT)
(def prefix-query #(PrefixQuery. %1))
(def term #(Term. %1 %2))

(defonce sanalyzer (new StandardAnalyzer))

(defn create-index [] (FSDirectory/open (.toPath (File. "./data"))))

#_(defn create-index [] (RAMDirectory.))

(defn add-doc
  [w book]
  (let [[field1 field2] book
        document        (new Document)
        field1          (StringField. "field1" field1 Field$Store/YES)
        field2          (StringField. "field2" field2 Field$Store/YES)]
    (.add document field1)
    (.add document field2)
    (.addDocument w document)))

(def docs
  [["ffg" "aab"]
   ["fgg" "bba"]
   ["fff" "aba"]
   ["ggf" "aba"]
   ["ggg" "baa"]])

(defn index-documents! [index docs]
  (with-open [w    (IndexWriter. index (IndexWriterConfig. sanalyzer))]
    (doseq [doc docs] (add-doc w doc))))

(defn doc->map
  [doc]
  (reduce #(assoc %1 (keyword (.name %2)) (.stringValue  %2))
          {}
          (.getFields doc)))

(defn query-lucene
  [index query]
  (let [index-reader (DirectoryReader/open index)
        searcher     (IndexSearcher. index-reader)
        docIds       (map #(.doc %)
                          (-> searcher
                              (.search query 10)
                              (.scoreDocs)))
        docs         (map #(.doc searcher %) docIds)]
    (map doc->map docs)))

(defn example-query-1 []
  (let [tq1     (prefix-query (term "field1" "g"))
        tq2     (prefix-query (term "field2" "a"))
        queries [[tq1 MUST_NOT]
                 [tq2 MUST]]
        builder (reduce (fn [builder [query occur]]
                          (.add builder query occur))
                        (BooleanQuery$Builder.)
                        queries)]
    (.build builder)))

(defn example-query-2 []
  (-> String
      (make-array 0)
      (MultiFieldQueryParser. sanalyzer)
      (.parse "NOT field1: g* AND field2: a*")))

(defn -main [& _args]
  (let [index (main/create-index)]
    (main/index-documents! index main/docs)
    (prn (main/query-lucene index (example-query-1)))
    (prn (main/query-lucene index (example-query-2)))))
