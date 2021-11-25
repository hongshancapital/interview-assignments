//用户注册校验
var UserRegValidate = {
    _exp : {
        'username' : /^[a-zA-Z_][a-zA-Z0-9_\-]+$/ ,//用户名
        'password_length' : /^.{6,}$/, //密码长度必须大于 6
        'password_hasupper' : /[A-Z]+/, //密码里有大写字母
        'password_haslower' : /[a-z]+/, //密码里有小写字母
        'password_hasnumber' : /[0-9]+/, //密码里有数字
        'number' : /[0-9]+/, //纯数字
    },
    isUsername : function(v){
        if (!UserRegValidate._exp.username.test(v)){
            alert('用户名格式错误，只能由英文、下划线、数字组成，并以英文或下划线开头，不能以数字开头')
            return false
        }
        return true
    },
    isPassword : function(v) {
        if (!UserRegValidate._exp.password_length.test(v)){
            alert('密码长度必须大于6位')
            return false
        }

        var j = 0
        var _tmpCompareArray = new Array()
        for (var i=0,l=v.length;i<l;i++){
            if (!UserRegValidate._exp.number.test(v[i])){
                j = 0
                _tmpCompareArray = new Array()
                continue
            }
            _tmpCompareArray[j++] = v[i]
            if (j>=3){
                if (parseInt(_tmpCompareArray[0])+1 == parseInt(_tmpCompareArray[1]) && parseInt(_tmpCompareArray[1])+1 == parseInt(_tmpCompareArray[2])){
                    alert('密码中不能有三个以上的连续数字')
                    return false
                }else{
                    j = 0
                    _tmpCompareArray = new Array()
                    continue
                }
            }
        }

        if (UserRegValidate._exp.password_hasupper.test(v) && UserRegValidate._exp.password_haslower.test(v)){
            return true
        }
        if (UserRegValidate._exp.password_hasupper.test(v) && UserRegValidate._exp.password_hasnumber.test(v)){
            return true
        }
        if (UserRegValidate._exp.password_haslower.test(v) && UserRegValidate._exp.password_hasnumber.test(v)){
            return true
        }

        alert('密码中必须有大写字母，小写字母或数字中的两项')
        return false
    }
};