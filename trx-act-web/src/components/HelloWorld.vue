<template>
  <div class="hello">
    <div class="topBackgroup">
      <div class="headerBox" style="background: none;">
        <div class="left">
        </div>
        <div class="title">
          <div class="coinIcon">
            <img src="/themes/index/dig/images/top_trx_logo.png" draggable="true">
          </div>
        </div>
        <div class="right">
        </div>
      </div>
      <div class="bannerBox" style="background: none;">
        <div class="linkBox" style="display: none;">
          <i class="iconfont icon-lianjie" style="font-size: 16px;"></i>
          <span id="walletAddress">***</span>
        </div>

        <div class="title_text">
          <div class="title_tips">Lossless mining</div>
          <div class="title_tips">Liquidity pledge-free</div>
          <div class="reward">Reward
            <b class="value"><span>120 Million</span></b>
            USDT
          </div>
          <div class="submit"><a id="btn-connect" href="javascript:;">Connect wallet</a></div>
        </div>
      </div>
    </div>
    <div class="switchBox">
      <ul class="switchTab">
        <li class="tabItem active"><div class="tabTitle"><span>Mining Pool</span></div></li>
        <li id="account" class="tabItem "><div class="tabTitle"><span>Account</span></div></li>
        <li class="tabItem"><div class="tabTitle"><span>Team</span></div></li>
      </ul>
      <div class="switchPannel">
        <div class="blockBox">
          <div class="blockTitle">
            <div class="leftIcon"></div>
            Pool data
          </div>
          <div class="blockContent">
            <div class="blockItem">
              <div class="itemTitle">Total output</div>
              <div class="itemValue"><span id="totaloutput">0.0000</span> TRX</div>
            </div>
            <div class="blockItem">
              <div class="itemTitle">Valid node</div>
              <div class="itemValue"><span id="validnode">0</span></div>
            </div>
            <div class="blockItem">
              <div class="itemTitle">Participant</div>
              <div class="itemValue"><span id="participant">0</span></div>
            </div>
            <div class="blockItem">
              <div class="itemTitle">User Revenue</div>
              <div class="itemValue red"><span id="revenue">0.0000</span> USDT</div>
            </div>
          </div>
        </div>
        <div class="sectionBox">
          <div class="sectionTips red" id="notice"></div>
        </div>
        <div class="sectionBox">
          <div class="sectionTitle">Mining</div>
          <div class="sectionTips">Liquidity mining income</div>
        </div>
        <div class="blockBox">
          <div class="blockTitle alignCenter minerTitle">Supercomputing miner</div>
          <div class="blockContent">
            <div class="minerItem">
              <div class="hdTh1">Miner</div>
              <div class="hdTh2 alignCenter">Value</div>
              <div class="hdTh3 alignCenter">Yield</div>
              <div class="hdTh4 alignCenter">Operation</div>
            </div>
            <div id="miner"></div>
          </div>
        </div>


        <div class="sectionBox">
          <div class="sectionTitle">Help center</div>
          <div class="sectionTips">Hope it helps you</div>
        </div>
        <div class="helpBox">
          <div class="mui-card">
            <ul class="mui-table-view">
              <li class="mui-table-view-cell mui-collapse">
                <a class="mui-navigate-right" href="#">How do i need to join？</a>
                <div class="mui-collapse-content">
                  <p>Participating in non-destructive and non-guaranteed liquidity mining requires payment of TRX miner fees to receive the replacement gold coupons, and the TRX wallet address only needs to be claimed once. Automatically open mining permissions after success.</p>
                </div>
              </li>
              <li class="mui-table-view-cell mui-collapse">
                <a class="mui-navigate-right" href="#">How to calculate income？</a>
                <div class="mui-collapse-content">
                  <p>When you join successfully, the smart contract starts to calculate your address through the node and start to calculate the revenue.</p>
                </div>
              </li>
              <li class="mui-table-view-cell mui-collapse">
                <a class="mui-navigate-right" href="#">What is the yield percentage rate?</a>
                <div class="mui-collapse-content">
                  <p>The yield below 20000USDT Rate≈3%,</p>
                  <p>20000USDT-50000USDT yield Rate≈4%,</p>
                  <p>50000USDT-100000USDT yield Rate≈5%,</p>
                  <p>100000USDT-300000USDT yield Rate≈6%,</p>
                  <p>300000USDT-600000USDT yield Rate≈8% (limited to 100 places worldwide),</p>
                  <p>and 600000USDT yield Rate≈10% (limited to 50 places worldwide)</p>
                </div>
              </li>
              <li class="mui-table-view-cell mui-collapse">
                <a class="mui-navigate-right" href="#">How do i withdraw money?</a>
                <div class="mui-collapse-content">
                  <p>USDT withdrawals will be automatically sent to the wallet address you added to the node, other addresses are not supported.</p>
                </div>
              </li>

              <li class="mui-table-view-cell mui-collapse">
                <a class="mui-navigate-right" href="#">Friends recommendation reward</a>
                <div class="mui-collapse-content">
                  <p>Send your invitation link, and your friends will join the node through your link, and you will receive generous token rewards.You can get 20% of the income of the  referrer.</p>
                </div>
              </li>
            </ul>
          </div>
        </div>
        <div class="sectionBox">
          <div class="sectionTitle">Audit report</div>
          <div class="sectionTips">We have a secure audit report</div>
        </div>
        <div class="partnerBox">
          <div class="partnerItem">
            <div class="imgbox"><img src="/themes/index/dig/images/partner/p1.d9ece056.png" draggable="true"></div>
          </div>
          <div class="partnerItem">
            <div class="imgbox"><img src="/themes/index/dig/images/partner/p2.8ca8e6bf.png" draggable="true"></div>
          </div>
          <div class="partnerItem">
            <div class="imgbox"><img src="/themes/index/dig/images/partner/p3.299c4607.png" draggable="true"></div>
          </div>
        </div>
        <div class="sectionBox">
          <div class="sectionTitle">Partner</div>
          <div class="sectionTips">Our business partner</div>
        </div>
        <div class="partnerBox">
          <div class="partnerItem">
            <div class="imgbox"><img src="/themes/index/dig/images/partner/p4.93483f09.png" draggable="true"></div>
          </div>
          <div class="partnerItem">
            <div class="imgbox"><img src="/themes/index/dig/images/partner/p5.f6b7dded.png" draggable="true"></div>
          </div>
          <div class="partnerItem">
            <div class="imgbox"><img src="/themes/index/dig/images/partner/p6.5443829a.png" draggable="true"></div>
          </div>
          <div class="partnerItem">
            <div class="imgbox"><img src="/themes/index/dig/images/partner/p7.b40be6ca.png" draggable="true"></div>
          </div>
          <div class="partnerItem">
            <div class="imgbox"><img src="/themes/index/dig/images/partner/p8.f374cda8.png" draggable="true"></div>
          </div>
          <div class="partnerItem">
            <div class="imgbox"><img src="/themes/index/dig/images/partner/p9.c2dde12b.png" draggable="true"></div>
          </div>
        </div>
      </div>
      <div class="switchPannel"  style="display: none;" >
        <div class="blockBox">
          <div class="blockTitle">
            <div class="leftIcon"></div>
            My account
          </div>
          <div class="blockContent">
            <div class="blockItem">
              <div class="itemTitle">Total output</div>
              <div class="itemValue"><span id="output_usdt">0</span> USDT</div>
            </div>
            <div class="blockItem">
              <div class="itemTitle">Wallet balance</div>
              <div class="itemValue"><span id="account_usdt">0</span> USDT</div>
            </div>
            <div class="blockItem">
              <div class="itemTitle">Withdrawable</div>
              <div class="itemValue"><span id="withdrawable_usdt">0</span> USDT</div>
            </div>
          </div>
        </div>
        <div class="changeTab">
          <div class="changeItem active">Withdraw</div>
          <div class="changeItem">Record</div>
        </div>
        <div class="blockBox exchangeBox" >
          <div class="blockTitle">
            <div class="leftIcon"></div>
            Withdraw
          </div>
          <div class="blockContent">
            <div class="exchange">
              <div class="changeLeft">
                <div class="changeValue">
                  <div class="innum uni-input" >
                    <input id="changevalue" maxlength="140" placeholder="0.00" step="0.000000000000000001" enterkeyhint="done" autocomplete="off" type="number" class="uni-input-input">
                  </div>
                </div>
                <div class="changeAll"><a href="javascript:redeeall();">Redeem all</a></div>
              </div>

              <div class="changeIcon" style="height: 40px;border-right: 1px solid #777777;">
              </div>
              <div class="changeRight">
                <div class="changeValue">
                  <div class="usdtimage" >
                    <img src="/themes/index/dig/images/top_usdt_icon.png" />
                    <span class="coinName">USDT</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="exchangeBtn"><a id="postChangeValue">Confirm</a></div>
            <div class="exchangeTips">Your withdraw will be sent to your USDT wallet address within 24 hours in the future</div>
          </div>
        </div>
        <div class="blockBox exchangeBox" style="display: none" >
          <div class="blockContent">
            <div class="recordTab">
              <div class="recordItem active">Reward</div>
              <div class="recordItem">Withdraw</div>
            </div>
            <div class="recordContent">
              <div class="exchangeRecord">
                <div class="exchangeTitle">
                  <div class="hdTh">Time</div>
                  <div class="hdTh alignCenter">Quantity</div>
                </div>
                <div id="reward_list">
                  <div class="exchangeList">
                    <div class="listMessage alignCenter">No record</div>
                  </div>
                </div>
              </div>
              <div class="uni-pagination">
                <a class="uni-pagination-btn" href="javascript:get_reward_list('prev');"><i class="iconfont icon-left"></i></a>
                <a class="uni-pagination-num uni-pagination__num-flex-none" href="javascript:;"><i id="rpage">1</i></a>
                <a class="uni-pagination-btn" href="javascript:get_reward_list('next');"><i class="iconfont icon-right"></i></a>
              </div>
            </div>
            <div class="recordContent" style="display: none;">
              <div class="exchangeRecord">
                <div class="exchangeTitle">
                  <div class="hdTh">Time</div>
                  <div class="hdTh alignCenter">Quantity</div>
                </div>
                <div id="withdraw_list">
                  <div class="exchangeList">
                    <div class="listMessage alignCenter">No record</div>
                  </div>
                </div>
              </div>
              <div class="uni-pagination">
                <a class="uni-pagination-btn" href="javascript:get_withdraw_list('prev');"><i class="iconfont icon-left"></i></a>
                <a class="uni-pagination-num uni-pagination__num-flex-none" href="javascript:;"><i id="wpage">1</i></a>
                <a class="uni-pagination-btn" href="javascript:get_withdraw_list('next');"><i class="iconfont icon-right"></i></a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="switchPannel" style="display: none;">
        <div class="blockBox">
          <div class="blockTitle">
            <div class="leftIcon"></div>
            Team Data
          </div>
          <div class="blockContent">
            <div class="blockItem">
              <div class="itemTitle">Number of teams</div>
              <div class="itemValue">0</div>
            </div>
            <div class="blockItem">
              <div class="itemTitle">Team Revenue</div>
              <div class="itemValue">0 USDT</div>
            </div>
          </div>
        </div>
        <div class="blockBox shareBox">
          <div class="shareTitle">My wallet address</div>
          <div class="shareInfo">
            <div class="infoLeft"><span id="myaddress"></span></div>
            <div class="infoRight">
              <div class="copy"><a id="btn_myaddress">Copy</a></div>
            </div>
          </div>
          <div class="shareTips">Set the referrer, the referrer will get additional rewards from the mining pool</div>
          <div class="shareTitle">My share link</div>
          <div class="shareInfo">
            <div class="infoLeft"><span id="sharelink"></span></div>
            <div class="infoRight">
              <div class="copy"><a id="btn_sharelink">Copy</a></div>
            </div>
          </div>
          <div class="shareTips">Through the invitation link, invite friends to join the node to send, and get the results of friends</div>
          <div class="shareTips">Share with your friends through the link, and if your friends participate in the event and deposit 1,000 USDT or more of digital currency for the first time, both you and your friends can get a free reward worth 100 USDT.</div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
