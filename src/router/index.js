import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import ToDo from '@/components/ToDo'
import Finish from '@/components/Finish'
Vue.use(Router);

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/toDoForm',
    name: 'ToDo',
    component: ToDo
  },
  {
    path: '/finishForm',
    name: 'Finish',
    component: Finish
  },
];

const router = new Router({mode: 'history', routes});

router.beforeEach((to, from, next) => {
  if (to.matched.length === 0) {
    from.name ? next({ name: from.name }) : next('/');
  } else {
    next();
  }
});

export default router;

