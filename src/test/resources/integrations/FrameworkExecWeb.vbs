Dim logStream
Dim dataExecution
Dim device
Dim app
Dim platform
Dim ciclo
Dim version
Dim appName
Dim mvnResult
Dim client
Dim secret
Dim browser
Dim resultTag
Dim fileName
Dim folder
Dim webTest
Dim apiTest
Dim mainframeTest
Dim gitExecution
Dim objNodeList
Dim userGit
Dim passwordGit
Dim branch
Dim serverGit
Dim ambiente
Dim userBancoBradesco
Dim passwordBancoBradesco
Dim mobileTest
Dim rotaGit

Public Function cmdExecute(CurrentTestSet, CurrentTSTest, CurrentRun, FolderCurrent)
	On Error Resume Next
	TDOutput.Print "Linha 1"
	gitExecution = true
	mobileTest = false

	readXmlConfig

	If gitExecution = true Then
		TDOutput.Print "Git execution true"
		'Aqui deve ser informado a rota do projeto no git, fica entre a URL e o nome do repositório.
        'EX: https://bitbucket.bradescoseguros.com.br:8443/scm/aut/framework-aut-web.git = sc/aut
		rotaGit = "scm/aut"
		'Usuário padrão para o clone
		userGit = "I418455"
		'Senha do usuário padrão: Gere um access tokens, não informe sua senha aqui
		passwordGit = "STQxODQ1NQ=="
		'Branch padrão da execução
		branch = "master"
		'Host padrão do servidor git
		serverGit = "bitbucket.bradescoseguros.com.br:8443"
		'Todos os valores padrões acima podem ser sobrescritos em tempo de execução criando o arquivo xml conforme ex abaixo no caminho C:\Automacao\config_automation.xml
		'<?xml version="1.0"?>
		'<config>
		'	<git>
		'		<user>I999999</user>
		'		<password>knVodas459G8NcuAzxui</password>
		'		<branch>master</branch>
		'		<server>bitbucket.bradescoseguros.com.br:8443</server>
		'	</git>
		'</config>
		gitClone "framework-aut-web"
	Else
		TDOutput.Print "Git execution false"
		'Caso a execução seja realizada localmente informe o caminho do projeto em sua maquina
		folder = "C:\Automacao\framework-aut-web"
	End If

	CleanDir(folder)

    dataExecution = replace(replace(Now, "/", "-"), ":", ".")
	ReplaceTag GetParameter(CurrentTSTest, "TEST_TAG")
	'Recuperar o folder do alm para saber os parâmetros de execução
	GetFolderNames CurrentTestSet, CurrentTSTest
    'Inicia o resultado como não executado.
    CurrentRun.Status = "Not Completed"
    CurrentTSTest.Status = "Not Completed"
    IgnoreHtmlFormat  = True
	SetLog logStream, "----------------------------Executando----------------------------"
	MvnTest folder, CurrentTestSet, CurrentTSTest
    'Faz o parse dos resultados do Junit
    ParseJUnitResults folder, CurrentTSTest, CurrentRun
    'Fazer o upload dos arquivos no ALM
	TDOutput.Print "Linha 35"
    UploadLog folder, CurrentTSTest, CurrentRun
    CloseLog logStream, CurrentTSTest, CurrentRun
	If Err.Number <> 0 Then
        SetLog logStream, "Codigo possui erro: " & Err.Number & ", " & Err.Source & ", " & Err.Description
        Err.Clear
    End If
End Function

Function GetParameter(objTest, strParameterName)
	 Set StepParams = objTest.Params
	 With StepParams
		  For i = 0 To .Count
			  If (UCase(.ParamName(i)) = strParameterName) Then
				  GetParameter = ParseParameterValue(.ParamValue(i))
				  'SetLog logStream, "TEST_NAME - " & GetParameter
				  Exit Function
			  End If
		  Next
	End With
	If Err.Number <> 0 Then
		GetParameter = GetParameterHtml(objTest, strParameterName)
	End If
	GetParameter = ""
End  Function

Function readXmlConfig ()
	Dim varUser
    Dim varPassword
	Set xmlDoc = CreateObject("Msxml2.DOMDocument")
    xmlDoc.load("C:\Automacao\config_automation.xml")
    Set objNodeList = xmlDoc.getElementsByTagName("config")

    If objNodeList.length > 0 then
		If gitExecution = true Then
			Set nodeGit = objNodeList(0).getElementsByTagName("git")(0)
			userGit = nodeGit.getElementsByTagName("user")(0).Text
			passwordGit = nodeGit.getElementsByTagName("password")(0).Text
			branch = nodeGit.getElementsByTagName("branch")(0).Text
			serverGit = nodeGit.getElementsByTagName("server")(0).Text
		End If

		userBancoBradesco = ""
		passwordBancoBradesco = ""
		Set nodeMobileCenter = objNodeList(0).getElementsByTagName("chaveCorporativaBancoBradesco")(0)
		If Not nodeMobileCenter Is Nothing  Then
			userBancoBradesco = nodeMobileCenter.getElementsByTagName("user")(0).Text
			passwordBancoBradesco = nodeMobileCenter.getElementsByTagName("password")(0).Text
		End If
    Else
        TDOutput.Print "Arquivo config_automation.xml nao encontrado em C:\Automacao"
    End If
End Function

Function gitClone(projectName)
	Dim strCmdLine

	Set fs = CreateObject("Scripting.FileSystemObject")
	folder =  "C:\Temp\ALMExecutionGIT\" & projectName
	urlGit = "https://" & userGit & ":" & passwordGit & "@" & serverGit & "/" & rotaGit & "/" & projectName & ".git"
	'Wscript.Echo urlGit

	Set objShell = CreateObject("WScript.Shell")

	if fs.FolderExists(folder) Then
		if fs.FolderExists(folder & "\.git") then
			strCmdLine = "git -C " & folder & " checkout ."
			objShell.Run strCmdLine, 1, True
			strCmdLine = "git -C " & folder & " -c http.sslVerify=false pull origin " & branch
			objShell.Run strCmdLine, 1, True
		Else
			strCmdLine = "%comspec% /c rd /q /s " & folder
			objShell.Run strCmdLine, 1, True
			strCmdLine = "%comspec% /c git -c http.sslVerify=false clone -b " & branch & " " & urlGit & " " & folder
			objShell.Run strCmdLine, 1, True
		End If
	Else
		strCmdLine = "%comspec% /c git -c http.sslVerify=false clone -b " & branch & " " & urlGit & " " & folder
		'Wscript.Echo strCmdLine
		objShell.Run strCmdLine, 1, True
		if not fs.FolderExists(folder) Then
			'WScript.Echo "Nao foi possivel realizar o clone do projeto, verifique o nome do mesmo"
		End If
	End If
End  Function

Function GetParameterHtml(objTest, strParameterName)
        Set objParamFactory = objTest.ParameterValueFactory
    Set objParamList =  objParamFactory.NewList("")

    For Each objParam in objParamList
			TDOutput.Print objParam.Field("TP_NAME")
			If objParam.Field("TP_NAME") = strParameterName Then
			SetLog logStream, "name - " & objParam.ActualValue
			SetLog logStream, "Actual value - " & ParseParameterValue(objParam.ActualValue)
			GetParameter = ParseParameterValue(objParam.ActualValue)
           Exit Function
        End If
        Next
End  Function

Function GetFolderNames(CurrentTestSet, CurrentTSTest)
	Dim folders
	Dim folderPath
	folderPath = CurrentTestSet.testSetFolder.path
	TDOutput.Print folderPath
    'Localizar browser e device
	folders = split(CurrentTestSet.testSetFolder.path & "", "\")
	lenght = ubound(folders)
	If InStr(LCase(folderPath), "mobile") Then
		mobileTest = True
		device = folders(lenght)
		ambiente = folders(lenght-1)
	Else
		ambiente = folders(lenght)
	End If
	Set logStream = CreateLog(folder, CurrentTSTest)
	SetLog logStream, "ALM folder - " & CurrentTestSet.testSetFolder.path
End Function

Function ParseParameterValue(actualValue)
    actualValue = "" & actualValue
    If InStr(1, actualValue, "<html>") > 0 Then
        Dim document
        Set document = CreateObject("MSXML2.DOMDocument")
        document.loadXml actualValue
        ParseParameterValue = document.selectSingleNode("//div/font/span").text
    Else
        ParseParameterValue = actualValue
    End IF
End Function

Function MvnTest(folder, CurrentTestSet, CurrentTSTest)
	Dim strCmdLine
    Set objShell = CreateObject("WScript.Shell")
    SetLog logStream, "Mvn execution folder - " & folder
	If mobileTest = true Then
		TDOutput.Print "Teste mobile"
		strCmdLine = "%comspec% /c mvn -f """ & folder & """ test -Dvar.formatter=FatHtmlFormatter -Dvar.fmt_export=" & chr(34) & fileName & chr(34) & " -Dvar.deviceName=" & chr(34) & device & chr(34) &" -Dvar.userName="& userBancoBradesco &" -Dvar.password=" & passwordBancoBradesco & ""
	Else
		strCmdLine = "%comspec% /c mvn -f """ & folder & """ test -Dvar.formatter=FatHtmlFormatter -Dvar.fmt_export=" & chr(34) & fileName & chr(34) & " -Dvar.userName="& userBancoBradesco &" -Dvar.password=" & passwordBancoBradesco & ""
	End If
    strCmdLine = strCmdLine & " -Dtest.environment=" & ambiente & " -Dcucumber.options=""" & resultTag & """"
	SetLog logStream, "Command mvn - " & Replace(Replace (strCmdLine, userBancoBradesco, "*******"), passwordBancoBradesco, "********")
	TDOutput.Print "Executando o teste"
	objShell.Run strCmdLine & " > " & folder & "\target\logs\maven_exec-" & fileName & ".txt", 0, True
	Set file = CreateObject("Scripting.FileSystemObject").OpenTextFile(folder & "\target\logs\maven_exec-" & fileName & ".txt", 1)
	SetLog logStream, file.ReadAll()
	file.Close
	TDOutput.Print "Teste executado"
End  Function

Function ReplaceTag (tag)
	resultTag = Replace(Replace(Replace(Replace(Replace(tag, "and", ""), "@", ";"), " ", ""), ",", ""), "--tags", "")
	resultTag = Replace(resultTag, ";", " --tags @")
	resultTag = Right(resultTag, Len(resultTag) - 1)
	fileName = Replace(Replace(Replace(dataExecution & "-" & resultTag, "@", ""), "--tags ", ""), " ", "-")
End Function

Function CleanDir(baseFolder)
    Set fs = CreateObject("Scripting.FileSystemObject")
	Set folder = fs.GetFolder(baseFolder)
    Set listFiles = folder.Files

    For Each file in listFiles
    	filePath = LCase(file.Path)
        If (InStr(filePath, "results.xml") > 0) Or(InStr(filePath, "result.xml") > 0)  Then
        	fs.DeleteFile filePath
        End If
    Next

	If(fs.folderExists(baseFolder + "\target\logs")) Then
		Set folderFile = fs.GetFolder(baseFolder + "\target\logs")
		Set listFiles = folderFile.Files
		For Each file in listFiles
			filePath = LCase(file.Path)
			TDOutput.Print filePath
			fs.DeleteFile filePath
		Next
	Else
		If Not(fs.folderExists(baseFolder + "\target")) Then
			fs.CreateFolder(baseFolder + "\target")
		End If
		If Not(fs.folderExists(baseFolder + "\target\logs")) Then
			fs.CreateFolder(baseFolder + "\target\logs")
		End If
	End If
End Function

Function StringfyAndConvertToInt(nuableValue, defaultValue)
    Dim strValue
    strValue = "" & nuableValue ' CStr não funciona aqui, pois o valor pode ser nulo...
    If Len(strValue) = 0 Then ' O valor ta vazio
        StringfyAndConvertToInt = defaultValue
    Else
        StringfyAndConvertToInt = CInt(strValue)
    End If
End Function

Function ParseJUnitResults(baseFolder, CurrentTSTest, CurrentRun)
	Dim document, fs, folder, file, testSuite, testCase, nTests, nFails, strTests, failurePercentage, nTime, nTimeValue
	Set document = CreateObject("MSXML2.DOMDocument")
	Set fs = CreateObject("Scripting.FileSystemObject")
	Set folder = fs.GetFolder(baseFolder)
	CurrentTSTest.Status = "Failed"
	CurrentRun.Status = "Failed"
    SetLog logStream, "Procurando arquivo de resultado em: " & baseFolder
    For Each file in folder.Files
		filepath = LCase(file.path)
		if (instr(filepath, "results.xml") > 0) or(instr(filepath, "result.xml") > 0)  then
			SetLog logStream, "Arquivo encontado: " & filepath & ". Fazendo leitura do conteúdo"
			document.Load file.Path
			For Each testSuite In document.getElementsByTagName("testsuite")
				SetLog logStream, "Entrou for"
				nTests = StringfyAndConvertToInt(testSuite.getAttribute("tests"), 0)
				nFails = StringfyAndConvertToInt(testSuite.getAttribute("failures"), 0)
				nTime  = testSuite.getAttribute("time")
				SetLog logStream, "Converteu int"
				if nTests = 0 Then
					SetLog logStream, "Finalizando leitura. Nenhum teste foi executado"
					CurrentTSTest.Status = "No Run"
					CurrentRun.Status = "No Run"
				Else
					failurePercentage = (nFails / nTests) * 100
															SetLog logStream, "-----------------------------------------------Mvn Test Rsult---------------------------------------------------"
					SetLog logStream, "Executed: " & nTests & "         Passed: " & nTests - nFails & "           Failed: " & nFails & "."
					SetLog logStream, "Porcentagem de falha do arquivo: " & file.Name & ": " & failurePercentage & "%"
					if failurePercentage = 0 Then
						CurrentTSTest.Status = "Passed"
						CurrentRun.Status = "Passed"
						CurrentTSTest.Field("TC_USER_TEMPLATE_02") = ""
					Else
						CurrentTSTest.Status = "Failed"
						CurrentRun.Status = "Failed"
					End If
				End If
				if nTime <> "" Then
					nTimeValue = Split(nTime, ".")
					SetLog logStream, "Tempo da Execução: " & nTimeValue(0)
					CurrentRun.Field("RN_COMMENTS") = nTimeValue(0)
				End if
			Next
		end if
	Next
    SetLog logStream, "Todos arquivos *Result.xml e *Results.xml foram lidos"
End Function

Function UploadLog(baseFolder, CurrentTSTest, CurrentRun)
        SetLog LogStream, "Folder to upload results: " + baseFolder
    Set fs = CreateObject("Scripting.FileSystemObject")
        'documentHtml = baseFolder & "\target\logs\" & fileName & ".html"
        'if fs.folderExists(baseFolder) Then
        	'UploadAttachment documentHtml, CurrentTSTest
            'UploadAttachment documentHtml, CurrentRun
        'Else
        	SetLog LogStream, "Nenhuma evidência encontrada."
        'End If
End Function

Function CreateLog(logFolder, CurrentTSTest)
	Dim logStream
	Set fs = CreateObject("Scripting.FileSystemObject")
	TDOutput.Print logFolder & "\target\logs"
    Set folderLog = fs.GetFolder(logFolder & "\target\logs")
	Set logStream = folderLog.createTextFile("logExecution" & fileName & ".log")
	Set CreateLog = logStream
End Function

Function SetLog(logStream, logText)
        logStream.writeLine  Now & " - " & logText
		TDOutput.Print logText
End Function

Function CloseLog(logStream, CurrentTSTest, CurrentRun)
        logStream.close
        Set fs = CreateObject("Scripting.FileSystemObject")
        file = folder & "\target\logs\logExecution" & fileName & ".log"
		TDOutput.Print file
        if fs.fileExists(file) Then
                TDOutput.Print "Existe log"
                UploadAttachment file, CurrentTSTest
                UploadAttachment file, CurrentRun
        End if
        TDOutput.Print "ENCERRADO"
End Function