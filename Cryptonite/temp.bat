attrib +s "C:\Users\�����\Desktop\test2"
echo [.ShellClassInfo] >>C:\Users\�����\Desktop\test2\desktop.ini
echo ConfirmFileOp=0 >>C:\Users\�����\Desktop\test2\desktop.ini
echo NoSharing=1 >>C:\Users\�����\Desktop\test2\desktop.ini
echo IconFile=C:\Users\�����\Desktop\Cryptonite-master\Cryptonite\_folder.ico >>C:\Users\�����\Desktop\test2\desktop.ini
echo IconIndex=0 >>C:\Users\�����\Desktop\test2\desktop.ini
echo InfoTip=Cryptonite >>C:\Users\�����\Desktop\test2\desktop.ini
attrib +S +H C:\Users\�����\Desktop\test2\desktop.ini
taskkill /f /im explorer.exe
explorer