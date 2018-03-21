package team.antelope.fg.util.jdbc;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/*
 * BeanListHandler��������������м�¼ת����bean���󼯺ϵĴ�����
 * Ҫ����rs.getMetaData()
 * @author ���Ĳ�
 * @time:2017��11��12�� ����12:35:49
 * @Description:TODO
 */
public class BeanListHandler implements IResultSetHandler {

	private Class<?> mClass;
	
	public BeanListHandler(Class<?> cla){
		mClass = cla;
	}
	
	@Override
	public Object handler(ResultSet rs) {
		// TODO �Զ����ɵķ������
		System.out.println("start");
		if(rs == null){
			return null;
		}
		
		try {
			List<Object> beanList = new ArrayList<>();
			ResultSetMetaData metaData = rs.getMetaData();
			int colNum = metaData.getColumnCount();
			while(rs.next()){
				
				Object bean = mClass.newInstance();
				for(int i=1; i<=colNum; i++){
					Object object = rs.getObject(i);
					String colName = metaData.getColumnName(i);
					Field f = mClass.getDeclaredField(colName);
					f.setAccessible(true);
					// add at 17/12/30 for modify a error
					if(f.getType().getSimpleName().equals(Long.class.getSimpleName())){
						System.out.println(f.getType().getSimpleName() + ".. Long");
//						f.setLong(bean, new Long(String.valueOf(object))); ��ôд���Ǵ���ģ���Ϊf.setLong�Ǹ�long���͵ĸ�ֵ������Long
						if(object == null){
							object = 0L;
						}
						f.set(bean, new Long(String.valueOf(object)));	//��ʱ��Ӧ����f.set
						System.out.println("endLong");
					} else if (f.getType().getSimpleName().equals(long.class.getSimpleName())){
						System.out.println(f.getType().getSimpleName()+ ".. long");
						if(object == null){
							object = 0l;
						}
						f.setLong(bean, Long.parseLong(String.valueOf(object)));
						System.out.println("endlong");
					} else if (f.getType().getSimpleName().equals(float.class.getSimpleName())){
						System.out.println(f.getType().getSimpleName()+ ".. float");
						if(object == null){
							object = 0f;
						}
						f.setFloat(bean, Float.parseFloat(String.valueOf(object)));
						System.out.println("endfloat");
					} else if (f.getType().getSimpleName().equals(Float.class.getSimpleName())){
						System.out.println(f.getType().getSimpleName()+ ".. Float");
						if(object == null){
							object = 0F;
						}
//						f.setFloat(bean, Float.parseFloat(String.valueOf(object)));  setFloatֻ��float�����ֶζ�����Float����
						f.set(bean, Float.parseFloat(String.valueOf(object)));
						System.out.println("endFloat");
					}  else {
						f.set(bean, object);
					}
				}
				beanList.add(bean);
			}
			/*��������������������0*/
			return beanList.size() > 0 ? beanList : null;
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return null;
	}

}