var vm = new Vue({
    el: '#main-container',
    data:function(){
        return{
            deptList:"",
            name:''
        }
    },
    methods: {
        findDept:function(pageNum, pageSize){
            console.log("findDept");
            axios({url:path+"/dept/findDeptAndUserCount"})
                .then(response => {
                    this.deptList = response.data;
                    console.log(this.deptList);
                }).catch(function(error){console.log(error)});
        },
        add:function () {
            this.name=prompt("请输入部门名称： ");
            if (this.name){
                axios({url:path+"/dept/add",params:{"name":this.name}})
                    .then(response => {
                        this.deptList = response.data;
                        console.log(this.deptList);
                        window.location.href=path+'/dept/toList';
                    }).catch(function(error){console.log(error)});
            }
        }

    },
    created: function () {
        this.findDept();
    }
});