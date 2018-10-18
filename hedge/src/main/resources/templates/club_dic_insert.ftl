<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>俱乐部字典新增</title>
    <style>
    </style>
</head>
<body>

<center>
    <h2>新增俱乐部名称字典</h2>
    <form action="/setting/clubdic/insert" method="post">
        俱乐部标准：
        <select name="clubId">
            <option>---请选择---</option>
        <#list clubNameList as item>
            <option value=${item.clubId}>${item.clubName}</option>
        </#list>
        </select>
        </br>
        </br>
        别名：<input type="text" name="clubOtherName" value=""/><br/><br/>
        <input type="submit" value="新增" />
    </form>
</center>

<p>
    ${message}
</p>
</body>
</html>