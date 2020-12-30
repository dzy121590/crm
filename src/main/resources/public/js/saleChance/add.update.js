layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;


    /**
     * 监听submit事件
     * 实现营销机会的添加与更新
     */
    form.on("submit(addOrUpdateSaleChance)", function (data) {
        var index = layer.msg("数据提交中,请稍后...", {
            icon: 16, // 图 标
            time: false, // 不关闭
            shade: 0.8 // 设置遮罩的透明度
        });
        // 请求的地址
        var url = ctx + "/sale_chance/save";
        // 判断隐藏域中的ID是否为空，如果不为空则为修改操作
        if ($("input[name='id']").val()) {
            url = ctx + "/sale_chance/update";
        }
        // 发送ajax请求
        $.post(url, data.field, function (result) {
            // 操作成功
            if (result.code == 200) {
                // 提示成功
                layer.msg("操作成功！");
                // 关闭加载层
                layer.close(index);
                // 关闭弹出层
                layer.closeAll("iframe");
                // 刷新父页面，重新渲染表格数据
                parent.location.reload();
            } else {
                layer.msg(result.msg);
            }
        });
        return false; // 阻止表单提交
    });

    /**
     * 监听submit事件
     * 实现营销机会的添加与更新
     */
    form.on("submit(addOrUpdateSaleChance)", function (data) {
        // 提交数据时的加载层 （https://layer.layui.com/）
        var index = layer.msg("数据提交中,请稍后...",{
            icon:16, // 图 标
            time:false, // 不关闭
            shade:0.8 // 设置遮罩的透明度
    });
    // 请求的地址
    var url = ctx + "/sale_chance/save";
    // 判断隐藏域中的ID是否为空，如果不为空则为修改操作
    if ($("input[name='id']").val()) {
        url = ctx + "/sale_chance/update";
    }
    // 发送ajax请求
    $.post(url, data.field, function (result) {
         // 操作成功
        if (result.code == 200) {
            // 提示成功
            layer.msg("操作成功！");
            // 关闭加载层
            layer.close(index);
            // 关闭弹出层
            layer.closeAll("iframe");
            // 刷新父页面，重新渲染表格数据
            parent.location.reload();
        } else {
            layer.msg(result.msg);
        }
    });
    return false; // 阻止表单提交
});
})
;