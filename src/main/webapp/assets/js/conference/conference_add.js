var vm = new Vue({
    el: '#main-container',
    data:function(){
        return{
            userList:'',
            dept:'',
            conference:{
                deptId:''
            }
        }

    },
    methods: {
        findAddDetail:function(){
            console.log("findAddDetail");
            axios({url:path+"/conference/findAddDetail"})
                .then(response => {
                    console.log(response.data)
                    this.userList = response.data.users;
                    this.dept = response.data.dept;
                    this.conference.deptId=this.dept.id;
                    this.conference.publishDate=this.dataNow();
                    console.log(this.conference);
                    console.log(this.userList);
                    console.log(this.dept);
                }).catch(function(error){console.log(error)});
        },
        dataNow:function () {
            const dateTime = new Date();
            const YY = dateTime.getFullYear();
            const MM =
                dateTime.getMonth() + 1 < 10
                    ? '0' + (dateTime.getMonth() + 1)
                    : dateTime.getMonth() + 1;
            const D =
                dateTime.getDate() < 10 ? '0' + dateTime.getDate() : dateTime.getDate();

            return `${YY}-${MM}-${D}`;
        },
        add:function () {
            console.log("add");
            this.selectUserIds();
            console.log(this.conference);
            axios({url:path+"/conference/add",method:'post',data:this.conference})
                .then(response => {
                    console.log(response.data)
                    alert(response.data.msg);
                    if (response.data.flag){
                        //跳转
                        window.location.href=path+'/conference/toList';
                    }
                }).catch(function(error){console.log(error)});
        },
        selectUserIds:function () {
            const options = document.querySelector('#selectUsers').options
            const selectedValueArr = []
            for (let i = 0; i < options.length; i++) {
                // 如果该option被选中，则将它的value存入数组
                if (options[i].selected) {
                    selectedValueArr.push(options[i].value)
                }
            }
            this.conference.userIds=selectedValueArr;
        }

    },
    created: function () {
        this.findAddDetail();
    }
});