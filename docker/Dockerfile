FROM kpiyumal/blinkr-base
ARG release_dir
ARG system_dir
ENV HOME /root
WORKDIR $HOME/$system_dir
COPY $release_dir ./
WORKDIR $HOME/
COPY $release_dir/scripts/blinkr.sh ./
COPY $release_dir/scripts/blinkr-docker.sh ./
RUN chmod +x blinkr.sh
RUN chmod +x blinkr-docker.sh
ENTRYPOINT ["./blinkr-docker.sh"]
CMD []