<template>
  <div>
    <producer-base ref="producerBase" :keywordTip="keywordTip" :keywordName="keywordName" :colData="colData"
                   :subscribeDialogColData="subscribeDialog.colData" @on-enter="getList"
                    :search="search" :subscribeUrls="subscribeDialog.urls" @on-detail="handleDetail"/>
  </div>
</template>

<script>
import Vue from 'vue'
import producerBase from '../monitor/producerBase.vue'
import {getAppCode, yesOrNoBtnRender, openOrCloseBtnRender, clientTypeSelectRender,
  clientTypeBtnRender} from '../../utils/common.js'

export default {
  name: 'producer',
  components: {
    producerBase
  },
  props: {
    search: {// 查询条件，格式：topic:{id:0,code:'',namespace:{id:0,code:''}}
      type: Object
    }
  },
  data () {
    return {
      keywordTip: '请输入应用',
      keywordName: '应用',
      width: 80,
      colData: [
        {
          title: '应用',
          key: 'app.code',
          formatter(row) {
              return getAppCode(row.app, row.subscribeGroup)
          },
          render: (h, params) => {
            const app = params.item.app
            return h('d-button', {
                props: {
                    type: 'borderless',
                    color: 'primary'
                },
                style: {
                    color: '#3366FF'
                },
                on: {
                    click: () => {
                        this.$router.push({
                            name: `/${this.$i18n.locale}/application/detail`,
                            query: {
                                id: app.id,
                                app: app.code,
                                tab: 'producer'
                            }
                        })
                    }
                }
            }, app.code)
          }
        },
        {
          title: '主题',
          key: 'topic.code',
          width: 100
        },
        {
          title: '命名空间',
          key: 'namespace.code'
        },
        // {
        //   title: '负责人',
        //   key: 'owner.code'
        // },
        {
          title: '连接数',
          key: 'connections',
          width: 100,
          render: (h, params) => {
            const connections = params.item.connections
            const formatNumFilter = Vue.filter('formatNum')
            return h('label', formatNumFilter(connections))
          }
        },
        {
          title: '入队数',
          key: 'enQuence.count',
          width: 150,
          render: (h, params) => {
            const enQuence = params.item.enQuence
            if (!enQuence) {
              return h('label', '')
            } else {
              const formatNumFilter = Vue.filter('formatNum')
              return h('label', formatNumFilter(enQuence.count))
            }
          }
        },
        {
          title: '生产权重',
          key: 'config.weight'
        },
        {
          title: '限制IP发送',
          key: 'config.blackList',
          render: (h, params) => {
              const value = params.item.config ? params.item.config.blackList : ''
              return h('d-tooltip', {
                      props: {
                          content: value
                      }
                  }, [h('div', {
                      attrs: {
                          style: 'width: 100px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;'
                      }
                  }, value)]
              )
          }
        },
        {
          title: '单线程发送',
          key: 'config.single',
          render: (h, params) => {
            return yesOrNoBtnRender(h, params.item.config === undefined ? undefined : params.item.config.single)
          }
        },
        {
          title: '归档',
          key: 'config.archive',
          render: (h, params) => {
            return openOrCloseBtnRender(h, params.item.config === undefined ? undefined : params.item.config.archive)
          }
        },
        {
          title: '就近机房发送',
          key: 'config.nearBy',
          render: (h, params) => {
            return openOrCloseBtnRender(h, params.item.config === undefined ? undefined : params.item.config.nearBy)
          }
        },
        {
          title: '客户端类型',
          key: 'clientType',
          render: (h, params) => {
            return clientTypeBtnRender(h, params.item.clientType)
          }
        }
      ],
      // 订阅框
      subscribeDialog: {
        colData: [
          {
            title: 'ID',
            key: 'id'
          },
          {
            title: '应用代码',
            key: 'code'
          },
          {
            title: '客户端类型',
            key: 'clientType',
            render: (h, params) => {
              return clientTypeSelectRender(h, params, this.$refs.producerBase.$refs.subscribe)
            }
          }
        ],
        urls: {
          add: `/producer/add`,
          search: `/application/unsubscribed/search`
        }
      }
    }
  },
  methods: {
    getList () {
      this.$refs.producerBase.getList()
    },
    handleDetail (item) {
      this.$emit('on-detail', item)
    }
  }
}
</script>

<style scoped>

</style>
