;SwtProjectTemplate Installer Script

;--------------------------------
;Include Modern UI

  !include "MUI.nsh"

;--------------------------------
;General

  ;Name and file
  Name "SwtProjectTemplate"
  OutFile "SwtProjectTemplateInstaller.exe"

  ;Default installation folder
  InstallDir "$PROGRAMFILES\CHANGE_THIS_ORGANIZATION_NAME\SwtProjectTemplate"
  
  ;Get installation folder from registry if available
  InstallDirRegKey HKCU "Software\SwtProjectTemplate" ""

  RequestExecutionLevel user

;--------------------------------
;Interface Settings

  !define MUI_ABORTWARNING
	!define MUI_HEADERIMAGE ".\nsis\installersplash.bmp"
	!define MUI_HEADERIMAGE_BITMAP_NOSTRETCH
	!define MUI_HEADERIMAGE_BITMAP ".\nsis\installersplash.bmp"
	!define MUI_ICON ".\nsis\install.ico"
	!define MUI_UNICON ".\nsis\uninstall.ico"

;--------------------------------
;Pages

  !insertmacro MUI_PAGE_LICENSE ".\nsis\license.txt"
  !insertmacro MUI_PAGE_COMPONENTS
  !insertmacro MUI_PAGE_DIRECTORY
  !insertmacro MUI_PAGE_INSTFILES

  	!define MUI_FINISHPAGE_RUN
	!define MUI_FINISHPAGE_RUN_TEXT "Run SwtProjectTemplate?"
	!define MUI_FINISHPAGE_RUN_FUNCTION "LaunchLink"
	!insertmacro MUI_PAGE_FINISH
  
  !insertmacro MUI_UNPAGE_CONFIRM
  !insertmacro MUI_UNPAGE_INSTFILES
  
;--------------------------------
;Languages
 
  !insertmacro MUI_LANGUAGE "English"

;--------------------------------
;Installer Sections

Section "SwtProjectTemplate (required)" SecDummy

  SectionIn RO

    ;Files to be installed
    SetOutPath "$INSTDIR"
  
    File "SwtProjectTemplate.exe"
    File "launch4j\icon.ico"
    File "appsettings.ini"

	SetOutPath "$INSTDIR\lib"

    File "lib\commons-io-1.4.jar"
    File "lib\org.eclipse.core.commands_3.5.0.I20090525-2000.jar"
    File "lib\org.eclipse.equinox.common_3.5.0.v20090520-1800.jar"
    File "lib\org.eclipse.jface_3.5.0.I20090525-2000.jar"
    File "lib\org.eclipse.osgi_3.5.0.v20090520.jar"
    File "lib\org.eclipse.swt.win32.win32.x86_3.5.0.v3550b.jar"
    File "lib\org.eclipse.swt_3.5.0.v3550b.jar"

    SetOutPath "$INSTDIR"

    ; Write the installation path into the registry
    WriteRegStr HKLM SOFTWARE\SwtProjectTemplate "Install_Dir" "$INSTDIR"
  
    ; Write the uninstall keys for Windows
    WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\SwtProjectTemplate" "DisplayName" "SwtProjectTemplate"
    WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\SwtProjectTemplate" "UninstallString" '"$INSTDIR\uninstall.exe"'
    WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\SwtProjectTemplate" "NoModify" 1
    WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\SwtProjectTemplate" "NoRepair" 1
    WriteUninstaller "uninstall.exe"
  
SectionEnd

; Optional section (can be disabled by the user)
Section "Start Menu Shortcuts"
  CreateDirectory "$SMPROGRAMS\SwtProjectTemplate"
  CreateShortCut "$SMPROGRAMS\SwtProjectTemplate\Uninstall.lnk" "$INSTDIR\uninstall.exe" "" "$INSTDIR\uninstall.exe"
  CreateShortCut "$SMPROGRAMS\SwtProjectTemplate\SwtProjectTemplate.lnk" "$INSTDIR\SwtProjectTemplate.exe" "" "$INSTDIR\icon.ico"
SectionEnd

;--------------------------------
;Uninstaller Section

Section "Uninstall"

  ; Remove registry keys
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\SwtProjectTemplate"
  DeleteRegKey HKLM SOFTWARE\SwtProjectTemplate
  DeleteRegKey /ifempty HKCU "Software\SwtProjectTemplate"

	; Remove shortcuts
  RMDir /r "$SMPROGRAMS\SwtProjectTemplate"

  ; Remove directories used
  RMDir /r "$INSTDIR"

SectionEnd

Function LaunchLink
  ExecShell """$SMPROGRAMS\SwtProjectTemplate\SwtProjectTemplate.lnk"
FunctionEnd



