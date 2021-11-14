@echo on
@echo ====================================================================================
@echo $ code: https://gitee.com/owenwangwen/com.mysting.tomato-platform  					$
@echo $ doc: https://www.kancloud.cn/owenwangwen/com.mysting.tomato-platform  				$
@echo $ blog: https://blog.51cto.com/13005375   										$
@echo ====================================================================================

call mvn clean package -Dmaven.test.skip=true

pause