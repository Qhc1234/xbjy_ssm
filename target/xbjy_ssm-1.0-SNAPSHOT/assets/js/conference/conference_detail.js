var vm = new Vue({
    el: '#main-container',
    data:function(){
        return{
            id:'',
            conference: '',
            users:'',
            come:'',
            notCome:'',
            shouldCome:''
        }

    },
    methods: {
        //获取url中的参数
        getUrlParam:function (name) {
            let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            let r = window.location.search.substr(1).match(reg);  //匹配目标参数
            if (r != null) return unescape(r[2]); return null; //返回参数值
        },
        findConferenceById:function () {
            this.id=this.getUrlParam("id");
            console.log(this.id);
            axios({url:path+"/conference/findById",params:{"id":this.id}})
                .then(response => {
                    this.conference = response.data;
                    console.log(this.conference);
                }).catch(function(error){console.log(error)});
        },
        findUsersByCId:function () {
            this.id=this.getUrlParam("id");
            axios({url:path+"/conference/findUsersByCId",params:{"id":this.id}})
                .then(response => {
                    this.users = response.data;

                    let come='';
                    let notCome='';
                    let shouldCome='';
                    for (let i=0;i<this.users.length;i++){
                        shouldCome += this.users[i].real_name+'、';
                        if (this.users[i].status==0) {
                            notCome += this.users[i].real_name+'、';
                        }else {
                            come += this.users[i].real_name+'、';
                        }
                    }
                    this.come=come.substring(0,come.length-1);
                    this.notCome=notCome.substring(0,notCome.length-1);
                    this.shouldCome=shouldCome.substring(0,shouldCome.length-1);

                    console.log(this.come);
                    console.log(this.notCome);
                    console.log(this.shouldCome);
                    console.log(this.users);
                }).catch(function(error){console.log(error)});
        },
        join:function () {
            axios({url:path+"/conference/join",params:{"id":this.id}})
                .then(response => {
                    console.log(response.data);
                    alert(response.data.msg);
                    window.location.reload();
                }).catch(function(error){console.log(error)});
        },
        cancel:function () {
            axios({url:path+"/conference/cancel",params:{"id":this.id}})
                .then(response => {
                    console.log(response.data);
                    alert(response.data.msg);
                }).catch(function(error){console.log(error)});
        }
    },
    created: function () {
        this.findConferenceById();
        this.findUsersByCId();
    }
});


