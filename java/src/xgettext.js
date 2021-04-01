#!/usr/bin/node
var fs = require("fs");

var index = 2;
if (process.argv.length <= index)
    return;

var msgs = [ ];
var msgsSet = { };

var gettext = function(regex, content, filename) {
    var msgs = [ ];

    var pos = null;

    while ((pos = regex.exec(content)) !== null)
    {
        var msg = {
            msgid: pos[1].trim(),
            filename: filename,
            pos: pos.index
        };

        msgs.push(msg);
    }

    return msgs;
};

for(var index = 2; index != process.argv.length; ++index)
{
    var filename = process.argv[index];

    var content = fs.readFileSync(filename, "utf-8");

    msgs = msgs.concat(gettext(/<i:tr>(.*?)<\/i:tr>/gm, content, filename));
    msgs = msgs.concat(gettext(/base\:i18n\([\"\']{1}(.*?)[\"\']{1}\)/g, content, filename));
    msgs = msgs.concat(gettext(/_I\.mark\([\"\']{1}(.*?)[\"\']{1}\)/gm, content, filename));
}

for(var index in msgs)
{
    var msg = msgs[index];

    if (msgsSet[msg.msgid] != null)
        continue;

    msgsSet[msg.msgid] = msg;
}

console.log(`# SOME DESCRIPTIVE TITLE.
# Copyright (C) YEAR THE PACKAGE'S COPYRIGHT HOLDER
# This file is distributed under the same license as the PACKAGE package.
# FIRST AUTHOR <EMAIL@ADDRESS>, YEAR.
# 
#, fuzzy
msgid ""
msgstr ""
"Project-Id-Version: backend-api 0.0.1-SNAPSHOT\\n"
"Report-Msgid-Bugs-To: \\n"
"POT-Creation-Date: 2019-08-31 22:59+0800\\n"
"PO-Revision-Date: YEAR-MO-DA HO:MI+ZONE\\n"
"Last-Translator: FULL NAME <EMAIL@ADDRESS>\\n"
"Language-Team: LANGUAGE <LL@li.org>\\n"
"MIME-Version: 1.0\\n"
"Content-Type: text/plain; charset=UTF-8\\n"
"Content-Transfer-Encoding: 8bit\\n"
`);

for(var msgid in msgsSet)
{
    var msg = msgsSet[msgid];

    console.log(`#${msg.filename}:${msg.pos}`);
    console.log(`msgid ${JSON.stringify(msgid)}`);
    console.log(`msgstr ""`);
    console.log("");
}

