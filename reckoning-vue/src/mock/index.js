// 使用 Mock
var Mock = require('mockjs')

Mock.mock(/\/user\/login/, "post", {
    'code': 200,
    'data': {
        token: "1111111",
        userId: '1'
    }
})

Mock.mock(/\/user\/queryUserDetail/, "get", {
    'code': 200,
    'data': {
        userId: '1',
        mobileNo: '18812345678',
        userName: '灰烬',
        userRemarks: '备注'
    }
})

Mock.mock(/\/user\/queryUser/, "post", {
    'code': 200,
    'data': {
        userInfoByNamePhone: [{
                userId: '1',
                mobileNo: '18812345678',
                userName: '灰烬',
            },
            {
                userId: '2',
                mobileNo: '18812345678',
                userName: '灰烬2',
            },
            {
                userId: '3',
                mobileNo: '18812345678',
                userName: '灰烬3',
            }
        ]
    }
})