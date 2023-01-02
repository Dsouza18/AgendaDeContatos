/**
 * Confirmação de exclusão de um contato
 * @author Daniel Santos
 */

 function confirmar(idcon){
	 let resposta = confirm("Confirmar a exclusão desse contato ?")
	 if (resposta === true){
		 window.location.href = "delete?idcon=" + idcon
	 }
 }