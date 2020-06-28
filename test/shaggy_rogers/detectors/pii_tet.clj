(ns shaggy-rogers.detectors.pii-tet
  (:require [clojure.test :refer :all]
            [shaggy-rogers.detectors.pii :as pii]
            [mockfn.macros :as mfn.macro]
            [shaggy-rogers.diplomat.http-out :as http-out]))

;; Document source: https://www.conjur.com.br/dl/representacao-pgr-bolsonaro-jogo.pdf
(def document-with-pii "JAIR MESSIAS BOLSONARO, presi.jairbolsonaro@senado.leg.br, brasileiro, casado,
Deputado Federal, então candidato à Presidente da República, portador da carteira de
identidade SSP/DF nº 3.032.827, inscrito no CPF sob o nº 453.178.287-91, CNPJ de
campanha nº 31.214.261/0001-38, com escritório na Av. Rio Branco nº 245, 8º andar,
Centro, Rio de Janeiro-RJ e COLIGAÇÃO BRASIL ACIMA DE TUDO, DEUS
ACIMA DE TODOS, integrada pelos partidos políticos 17-PSL e 28-PRTB,
representada pelo Sr. Gustavo Bebianno Rocha, também Presidente Nacional do PSL,
brasileiro, divorciado, advogado, inscrito na OAB/RJ sob o nº. 81.620, com endereço no
SHN, Quadra 02, Bloco F, Ed. Executive Office Tower, Sala 1122, Asa Norte, Brasília/DF,
onde receberá intimações e notificações, vêm, respeitosamente, perante Vossa
Excelência, por seus advogados que esta subscrevem, apresentar pedido de providência,
nos termos abaixo explicitados")

;; https://izmcm.github.io/birolipsum/
(def document-without-pii "Você não combate violência com amor, combate com porrada, pô. Se bandido tem pistola, (a gente) tem que ter fuzil. Não pode mais contar piada, não pode mais ter uma liberdade nesse país. Tá, e daí? Já tá feito, já pegou fogo, quer que eu faça o quê? O meu nome é Messias, mas eu não tenho como fazer milagre. Eu não sou de extrema- direita. Sou admirador do (Donald) Trump. Ele quer a América grande, eu quero o Brasil grande. Se eu quiser entrar armado aqui, eu entro. As pessoas que têm mais cultura têm menos filhos. Eu sou uma exceção à regra, tenho cinco, está certo? Mas como regra é isso. \n")

(deftest handler
  (testing "testing pii handler fn"
    (mfn.macro/providing
      [(http-out/valid-email? "presi.jairbolsonaro@senado.leg.br") true]
      (testing "testing when there's a pii on the document"
        (is (= {:pii-detector  {:cpfs   #{"453.178.287-91"}
                                :emails #{"presi.jairbolsonaro@senado.leg.br"}}
                :text-document document-with-pii}
               (pii/handler {:text-document document-with-pii})))))

    (testing "testing when there ins't a pii on the document"
      (is (= {:text-document document-without-pii}
             (pii/handler {:text-document document-without-pii}))))))
