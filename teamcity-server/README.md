# Using TeamCity

This guide shows how to use TeamCity for the build pipeline.

## Option 1 - TeamCity inside minikube, sharing its docker daemon

This has the advantage of not having to setup a Docker registry.

### Minikube

Install minikube and start a new cluster with `minikube start`.

**Consider configuring at least 4GB or RAM for the VM. The default 2GB is not enough.**

The following addons should be initially enabled:

    minikube addons list
    - addon-manager: enabled
    - coredns: disabled
    - dashboard: enabled
    - default-storageclass: enabled
    - efk: disabled
    - freshpod: disabled
    - heapster: disabled
    - ingress: disabled
    - kube-dns: enabled
    - metrics-server: disabled
    - nvidia-driver-installer: disabled
    - nvidia-gpu-device-plugin: disabled
    - registry: disabled
    - registry-creds: disabled
    - storage-provisioner: enabled

Enable `ingress` with `minikube addons enable ingress`.

Enable `heapster` with `minikube addons enable heapster`.

Install the server-side component of Helm with `helm init`.

### Build custom TeamCity Agent image

**Important** make sure you use minikube's docker host with `minikube docker-env`.

*Please note that TeamCity images are big so pulling them might take a while.*

Build the custom image with:

    docker build -t custom-agent -f ./Dockerfile-agent .

### Install

Edit the teamcity yaml files and replace the following absolute path prefix
`/c/Users/ngeor/Projects/github/blog-helm/teamcity-server/` with your own.

Then, install the yaml files:

    kubectl apply -f teamcity-server.yaml
    kubectl apply -f teamcity-agent.yaml

### Configure

Visit http://192.168.99.100:30200/ to configure TeamCity.

- create admin account
- upload SSH key named "ENVY" which can read/write the git repository
- create TeamCity project named "Blog Helm" (id should be BlogHelm)
- create VCS root for URL `git@github.com:ngeor/blog-helm.git`
  - name "Blog Helm"
  - id "BlogHelm_BlogHelm"
  - branch specification `+:refs/heads/*`
  - authentication method: Uploaded key ("ENVY")
- enable Versioned Settings
  - Synchronization Enabled
  - Project settings VCS root: "Blog Helm"
  - Use Settings from VCS
  - Store secure values outside of VCS
  - Settings format: Kotlin
  - at the prompt, select "Import settings from VCS"

Set an environment variable on the root project named `KUBECTL_CONFIG` which
will contain the base64 encoded contents of your kube config. You can create
that value with `kubectl config view --flatten | base64 -w 0`

### DNS

The following commands will allow minikube (and Docker Toolbox if you use it) to
use whatever dummy hosts you add in your `C:\Windows\System32\drivers\etc\hosts` file.

    # for Docker Toolbox
    C:\Program Files\Oracle\VirtualBox> .\VBoxManage.exe modifyvm default --natdnshostresolver1 on
    # for minikube
    C:\Program Files\Oracle\VirtualBox> .\VBoxManage.exe modifyvm minikube --natdnshostresolver1 on

Reference: https://www.virtualbox.org/manual/ch09.html#nat-adv-dns

Add the following entries to your hosts file:

    # minikube
    192.168.99.101 test.blog-helm.local
    192.168.99.101 acc.blog-helm.local
    192.168.99.101 blog-helm.local

The IP needs to match minikube's IP, which you can get with `minikube ip`.
