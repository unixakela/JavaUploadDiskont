Set objShell = WScript.CreateObject("WScript.Shell")
Set FSO = CreateObject("Scripting.FileSystemObject")
Set F = FSO.GetFile(Wscript.ScriptFullName)
path = FSO.GetParentFolderName(F)+"\out\artifacts\JavaUploadDiskont\JavaUploadDiskont.jar"
'MSgBox path
objShell.Run(path), 1, True