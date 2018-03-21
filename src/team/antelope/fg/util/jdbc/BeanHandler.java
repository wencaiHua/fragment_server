package team.antelope.fg.util.jdbc;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
/*
 * BeanHandler�����������һ�м�¼ת����bean����Ĵ�����
 * Ҫ����rs.getMetaData()
 * @author ���Ĳ�
 * @time:2017��11��12�� ����12:35:49
 * @Description:TODO
 */
public class BeanHandler implements IResultSetHandler {
	
	private Class<?> mClass;
	
	public BeanHandler(Class<?> cla){
		mClass = cla;
	}
	@Override
	public Object handler(ResultSet rs) {
		if(rs == null){
			return null;
		}
		try {
			if(!rs.next()){
				return null;
			}
			Object bean = mClass.newInstance();
			ResultSetMetaData metaData = rs.getMetaData();
			int colNum = metaData.getColumnCount();
			for(int i=1; i<= colNum; i++){
				String colName = metaData.getColumnName(i);//��ȡ������
				Object colData = rs.getObject(i);	//��ȡ������е�����
				Field f = mClass.getDeclaredField(colName);//��ȡ������bean���ֶ�f��Ҫ������ݿ������һ��
				f.setAccessible(true);
				if(f.getType().getSimpleName().equals(Long.class.getSimpleName())){
					System.out.println(f.getType().getSimpleName() + ".. Long");
//					f.setLong(bean, new Long(String.valueOf(object))); ��ôд���Ǵ���ģ���Ϊf.setLong�Ǹ�long���͵ĸ�ֵ������Long
					if(colData == null){
						colData = 0L;
					}
					f.set(bean, new Long(String.valueOf(colData)));	//��ʱ��Ӧ����f.set
					System.out.println("endLong");
				} else if (f.getType().getSimpleName().equals(long.class.getSimpleName())){
					System.out.println(f.getType().getSimpleName()+ ".. long");
					if(colData == null){
						colData = 0l;
					}
					f.setLong(bean, Long.parseLong(String.valueOf(colData)));
					System.out.println("endlong");
				} else if (f.getType().getSimpleName().equals(float.class.getSimpleName())){
					System.out.println(f.getType().getSimpleName()+ ".. float");
					if(colData == null){
						colData = 0f;
					}
					f.setFloat(bean, Float.parseFloat(String.valueOf(colData)));
					System.out.println("endfloat");
				} else if (f.getType().getSimpleName().equals(Float.class.getSimpleName())){
					System.out.println(f.getType().getSimpleName()+ ".. Float");
					if(colData == null){
						colData = 0F;
					}
//					f.setFloat(bean, Float.parseFloat(String.valueOf(object)));  setFloatֻ��float�����ֶζ�����Float����
					f.set(bean, Float.parseFloat(String.valueOf(colData)));
					System.out.println("endFloat");
				}  else {
					f.set(bean, colData);
				}
			}
			
			return bean;
		} catch (InstantiationException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (SQLException e) {
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