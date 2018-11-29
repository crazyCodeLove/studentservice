package com.sse.domain;

import java.util.List;

public class EurekaApplication {

    private Application application;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public static class Application {

        private String name;

        private List<Instance> instance;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Instance> getInstance() {
            return instance;
        }

        public void setInstance(List<Instance> instance) {
            this.instance = instance;
        }

        public static class Instance {
            private String instanceId;

            private String hostName;

            private String ipAddr;

            private String status;

            private String homePageUrl;

            public String getInstanceId() {
                return instanceId;
            }

            public void setInstanceId(String instanceId) {
                this.instanceId = instanceId;
            }

            public String getHostName() {
                return hostName;
            }

            public void setHostName(String hostName) {
                this.hostName = hostName;
            }

            public String getIpAddr() {
                return ipAddr;
            }

            public void setIpAddr(String ipAddr) {
                this.ipAddr = ipAddr;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getHomePageUrl() {
                return homePageUrl;
            }

            public void setHomePageUrl(String homePageUrl) {
                this.homePageUrl = homePageUrl;
            }

        }
    }
}
