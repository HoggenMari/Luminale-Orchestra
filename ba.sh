sudo -S mkdir -p /var/lock
chmod +x ./ba.sh
sudo -S route -nv add -net 224.1.1.1 -interface en0
