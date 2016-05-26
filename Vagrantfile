# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|
  config.vm.box = "ubuntu/trusty64"
  config.vm.synced_folder ".", "/vagrant"
  config.vm.provider "virtualbox" do |v|
    v.memory = 3072
  end
  config.vm.define :dev do |dev|
    dev.vm.network :forwarded_port, host: 8080, guest: 8080
    dev.vm.provision "shell", path: "bootstrap.sh"
    dev.vm.provision :shell, inline: 'ansible-playbook /vagrant/ansible/dev.yml -c local -v'
    dev.vm.hostname = "lyrics-engine-dev"
  end
  if Vagrant.has_plugin?("vagrant-cachier")
    config.cache.scope = :box
  end
end
