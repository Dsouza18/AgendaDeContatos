/**
 * Validação de formuçario 
 * @author Daniel Santos
 */

 function validar(){
	 let nome = frmContato.nome.value
	 let fone = frmContato.fone.value
	 if(nome === ""){
		 alert("Preenchar o campo Nome")
		 frmContato.nome.focus()
		 return false
	 }else if(fone === ""){
		 alert("Preenchar o campo Fone")
		 frmContato.fone.focus()
		 return false
	 } else{
		 document.forms["frmContato"].submit()
	 }
 }