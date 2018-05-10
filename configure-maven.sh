sed -i~ "/<servers>/ a\
<server>\
  <id>datavolo</id>\
  <username>${MAVEN_USERNAME}</username>\
  <password>${MAVEN_PASSWORD}</password>\
</server>" /usr/share/maven/conf/settings.xml
