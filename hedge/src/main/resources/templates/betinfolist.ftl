<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>最新盘口最优策略列表</title>
    <style>
        body{
            text-align:center;
        }
        p{
            font-size:24px;
            background-color:#ccc;
            text-align:center;
        }
        tr{
            width:800px;
            height:70px;
        }
        td{
            width:100px;
        }
    </style>
</head>
<body>
<center>
<table border="1" cellspacing="0" cellpadding="0">
    <caption><h2>最新盘口最优策略列表</h2></caption>
    <tr>
        <th>比赛</th>
        <th>主胜盘口选择及赔率</th>
        <th>平盘口选择及赔率</th>
        <th>客胜盘口选择及赔率</th>
        <th>返还率</th>
    </tr>
<#list betInfoOptList as item>
    <tr align="center">
        <td>${item.infoKey}</td>
        <td>${item.winPlatfrom}:${item.winOdds}</td>
        <td>${item.equalPlatform}:${item.equalOdds}</td>
        <td>${item.lossPlatform}:${item.lossOdds}</td>
        <td>${item.returnRateDecimal}</td>
    </tr>
</#list>
</table>
    </center>
</body>
</html>