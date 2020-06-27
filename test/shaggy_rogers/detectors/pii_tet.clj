(ns shaggy-rogers.detectors.pii-tet
  (:require [clojure.test :refer :all]
            [shaggy-rogers.detectors.pii :as pii]))

;; Document source: https://www.conjur.com.br/dl/representacao-pgr-bolsonaro-jogo.pdf
(def document-with-pii "JAIR MESSIAS BOLSONARO, brasileiro, casado,\nDeputado Federal, então candidato à Presidente da República, portador da carteira de\nidentidade SSP/DF nº 3.032.827, inscrito no CPF sob o nº 453.178.287-91, CNPJ de\ncampanha nº 31.214.261/0001-38, com escritório na Av. Rio Branco nº 245, 8º andar,\nCentro, Rio de Janeiro-RJ e COLIGAÇÃO BRASIL ACIMA DE TUDO, DEUS\nACIMA DE TODOS, integrada pelos partidos políticos 17-PSL e 28-PRTB,\nrepresentada pelo Sr. Gustavo Bebianno Rocha, também Presidente Nacional do PSL,\nbrasileiro, divorciado, advogado, inscrito na OAB/RJ sob o nº. 81.620, com endereço no\nSHN, Quadra 02, Bloco F, Ed. Executive Office Tower, Sala 1122, Asa Norte, Brasília/DF,\nonde receberá intimações e notificações, vêm, respeitosamente, perante Vossa\nExcelência, por seus advogados que esta subscrevem, apresentar pedido de providência,\nnos termos abaixo explicitados:")

;; https://izmcm.github.io/birolipsum/
(def document-without-pii "Você não combate violência com amor, combate com porrada, pô. Se bandido tem pistola, (a gente) tem que ter fuzil. Não pode mais contar piada, não pode mais ter uma liberdade nesse país. Tá, e daí? Já tá feito, já pegou fogo, quer que eu faça o quê? O meu nome é Messias, mas eu não tenho como fazer milagre. Eu não sou de extrema- direita. Sou admirador do (Donald) Trump. Ele quer a América grande, eu quero o Brasil grande. Se eu quiser entrar armado aqui, eu entro. As pessoas que têm mais cultura têm menos filhos. Eu sou uma exceção à regra, tenho cinco, está certo? Mas como regra é isso. \n")

(deftest handler
  (testing "testing pii handler fn"
    (testing "testing when there's a pii on the document"
      (is (= {:pii-detector  {:cpfs #{"453.178.287-91"}}
              :text-document document-with-pii}
             (pii/handler {:text-document document-with-pii}))))

    (testing "testing when there ins't a pii on the document"
      (is (= {:text-document document-without-pii}
             (pii/handler {:text-document document-without-pii}))))))