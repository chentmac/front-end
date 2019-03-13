function checkToday(date){
  date = new Date(date);
  let today = new Date();
  if(date.getFullYear() === today.getFullYear()&&date.getMonth() === today.getMonth() && date.getDate() === today.getDate()){
    return true;
  }
  return false;
}
export default {
  checkToday
}
